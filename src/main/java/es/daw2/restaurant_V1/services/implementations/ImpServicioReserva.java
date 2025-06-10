package es.daw2.restaurant_V1.services.implementations;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.reservas.ReservaClientRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaResponse;
import es.daw2.restaurant_V1.dtos.reservas.ReservaUpdateClientRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaUpdateRequest;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.exceptions.custom.NoTablesAvailableException;
import es.daw2.restaurant_V1.exceptions.custom.ReservaAlreadyFacturadaException;
import es.daw2.restaurant_V1.exceptions.custom.ReservaFechaPasadaException;
import es.daw2.restaurant_V1.models.Alergeno;
import es.daw2.restaurant_V1.models.Cliente;
import es.daw2.restaurant_V1.models.Mesa;
import es.daw2.restaurant_V1.models.Rango;
import es.daw2.restaurant_V1.models.Reserva;
import es.daw2.restaurant_V1.models.Reserva.ReservaStatus;
import es.daw2.restaurant_V1.repositories.AlergenoRepositorio;
import es.daw2.restaurant_V1.repositories.ClienteRepositorio;
import es.daw2.restaurant_V1.repositories.MesaRepositorio;
import es.daw2.restaurant_V1.repositories.ReservaRepositorio;
import es.daw2.restaurant_V1.services.email.EmailClient;
import es.daw2.restaurant_V1.services.interfaces.IFServicioRango;
import es.daw2.restaurant_V1.services.interfaces.IFServicioReserva;
import jakarta.transaction.Transactional;

@Service
public class ImpServicioReserva implements IFServicioReserva{

    @Autowired
    ReservaRepositorio reservaRepositorio;
    @Autowired
    MesaRepositorio mesaRepositorio;
    @Autowired
    ClienteRepositorio clienteRepositorio;
    @Autowired
    AlergenoRepositorio alergenoRepositorio;
    @Autowired
    EmailClient emailClient;
    @Autowired
    IFServicioRango servicioRango;

    private static final Logger LOG = LoggerFactory.getLogger(ImpServicioReserva.class);
    private static final Duration DURACION_RESERVA = Duration.ofHours(1).plusMinutes(55);

    @Override
    public Page<ReservaResponse> getAllReservas(Pageable pageable) {
        return reservaRepositorio.findAll(pageable)
                .map(this::composeReservaResponse);
    }

    @Override
    public Page<ReservaResponse> getTodayReservas(Pageable pageable){
        LocalDate today = LocalDate.now();
        LocalDateTime startOfDay = today.atStartOfDay();
        LocalDateTime endOfDay = today.plusDays(1).atStartOfDay();

        Page<Reserva> reservas = reservaRepositorio.findByReservaFechaBetween(startOfDay, endOfDay, pageable);
        return reservas.map(this::composeReservaResponse);
    }

    @Override
    public ReservaResponse findReservaById(Long id) {
        Reserva reservaFromDb = reservaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + id));

        return composeReservaResponse(reservaFromDb);
    }

    @Override
    public ReservaResponse findReservaByIdByClient(ReservaClientRequest reservaClientRequest, Long id) {
        Reserva reservaFromDb = reservaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + id));
        if(!reservaClientRequest.getClienteEmail().equals(reservaFromDb.getCliente().getEmail())){
            throw new IllegalArgumentException("Se ha encontrado la RESERVA, pero los EMAILS NO COINCIDEN");
        }
        return composeReservaResponse(reservaFromDb);
    }

    @Override
    public ReservaResponse crearReserva (ReservaRequest reservaRequest){

        if (reservaRequest.getReservaFecha().isBefore(LocalDateTime.now())) {
            throw new ReservaFechaPasadaException("La fecha de la reserva no puede ser anterior a la fecha de hoy");
        }
        
        Cliente clienteFromDb = clienteRepositorio.findByEmail(reservaRequest.getClienteEmail())
            .orElseGet( ()-> {
                    Cliente nuevoCliente = new Cliente();
                    nuevoCliente.setClienteNombre(reservaRequest.getClienteNombre());
                    nuevoCliente.setEmail(reservaRequest.getClienteEmail());
                    nuevoCliente.setClienteTlfn(reservaRequest.getClienteTlfn());
                    nuevoCliente.setPuntosFidelizacion(0L);

                    Rango rango = servicioRango.getRangoByPuntosMinimos(0L);
                    nuevoCliente.setRango(rango);

                    return clienteRepositorio.save(nuevoCliente);
                }
            );
        
        LocalDateTime fechaFin = reservaRequest.getReservaFecha().plus(DURACION_RESERVA);

        List<Mesa> mesasDisponibles = mesaRepositorio.findMesasDisponibles(
            reservaRequest.getNumeroPersonas(),
            reservaRequest.getReservaFecha(),
            fechaFin
        );

        if(mesasDisponibles.isEmpty()){
            throw new NoTablesAvailableException("No hay mesas disponibles para la fecha y número de personas indicados");
        }

        Mesa mesaAsignada = mesasDisponibles.get(0);

        Reserva reserva = new Reserva();
        reserva.setReservaFecha(reservaRequest.getReservaFecha());
        reserva.setReservaFin(fechaFin);
        reserva.setNumeroPersonas(reservaRequest.getNumeroPersonas());
        reserva.setReservaStatus(Reserva.ReservaStatus.ACTIVA);
        reserva.setCliente(clienteFromDb);
        reserva.setMesa(mesaAsignada);

        List<Alergeno> alergenos = new ArrayList<>();
        if(reservaRequest.getAlergenosIds() != null && !reservaRequest.getAlergenosIds().isEmpty()){
            alergenos = alergenoRepositorio.findAllById(reservaRequest.getAlergenosIds());
        }
        reserva.setAlergenos(alergenos);

        Reserva reservaGuardada = reservaRepositorio.save(reserva);

        ReservaResponse reservaResponse = composeReservaResponse(reservaGuardada);

        try {
            emailClient.sendConfirmationEmail(reservaResponse);
        } catch (Exception e) {
            LOG.warn("No se pudo enviar el correo de confirmación para la reserva {}: {}", reservaGuardada.getReservaId(), e.getMessage());
        }

        return reservaResponse;
    }

    @Override
    @Transactional
    public ReservaResponse actualizarReservaByClient(Long id, ReservaUpdateClientRequest reservaUpdateRequest) {

        Reserva reservaFromDb = reservaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + id));
        
        if(!reservaUpdateRequest.getClienteEmail().equals(reservaFromDb.getCliente().getEmail())){
            throw new IllegalArgumentException("No se puede modificar la reserva porque el EMAIL proporcionado no coincide con el asociado.");
        }

        if (reservaUpdateRequest.getNuevaFecha() != null && 
            reservaUpdateRequest.getNuevaFecha().isBefore(LocalDateTime.now())) {
            throw new ReservaFechaPasadaException("La fecha de la reserva no puede ser anterior a la fecha de hoy");
        }
                
        if(reservaFromDb.getReservaStatus() == ReservaStatus.FACTURADA){
            throw new ReservaAlreadyFacturadaException("La RESERVA con ID ["+id+"] ya ha sido facturada y no se puede modificar.");
        }

        LocalDateTime nuevaFecha = reservaUpdateRequest.getNuevaFecha() != null
                ? reservaUpdateRequest.getNuevaFecha()
                : reservaFromDb.getReservaFecha();

        LocalDateTime nuevaFechaFin = nuevaFecha.plus(DURACION_RESERVA);

        Integer nuevoNumeroPersonas = reservaUpdateRequest.getNuevoNumeroPersonas() != null
                ? reservaUpdateRequest.getNuevoNumeroPersonas()
                : reservaFromDb.getNumeroPersonas();

        // NOTA: SOLO SE BUSCA UNA NUEVA MESA SI CAMBIA EL NÚMERO DE PERSONAS
        if (reservaUpdateRequest.getNuevoNumeroPersonas() != null &&
            !nuevoNumeroPersonas.equals(reservaFromDb.getNumeroPersonas())) {

            List<Mesa> mesasDisponibles = mesaRepositorio.findMesasDisponibles(
                nuevoNumeroPersonas,
                nuevaFecha,
                nuevaFechaFin
            );

            if (mesasDisponibles.isEmpty()) {
                throw new NoTablesAvailableException("NO HAY MESAS DISPONIBLES PARA LA NUEVA FECHA Y NÚMERO DE PERSONAS");
            }

            reservaFromDb.setMesa(mesasDisponibles.get(0));

        } else if (reservaUpdateRequest.getNuevaFecha() != null) {
            // NOTA 2: SI EL NÚMERO DE PERSONAS NO CAMBIA, SOLO SE VERIFICA QUE LA MISMA MESA SIGUE LIBRE
            boolean mesaDisponible = reservaRepositorio.isMesaDisponibleEnRango(
                reservaFromDb.getMesa().getMesaId(),
                nuevaFecha,
                nuevaFechaFin,
                reservaFromDb.getReservaId()
            );

            if (!mesaDisponible) {
                throw new NoTablesAvailableException("LA MESA ACTUAL NO ESTÁ DISPONIBLE EN LA NUEVA FRANJA HORARIA");
            }
        }

        if (reservaUpdateRequest.getNuevaFecha() != null) {
            reservaFromDb.setReservaFecha(nuevaFecha);
            reservaFromDb.setReservaFin(nuevaFechaFin);
        }

        if (reservaUpdateRequest.getNuevoNumeroPersonas() != null) {
            reservaFromDb.setNumeroPersonas(nuevoNumeroPersonas);
        }

        if (reservaUpdateRequest.getNuevosAlergenos() != null) {
            if (reservaUpdateRequest.getNuevosAlergenos().isEmpty()) {
                reservaFromDb.getAlergenos().clear();
            } else {
                List<Alergeno> nuevosAlergenos = alergenoRepositorio.findAllById(reservaUpdateRequest.getNuevosAlergenos());
                reservaFromDb.setAlergenos(nuevosAlergenos);
            }
        }

        Reserva reservaActualizada = reservaRepositorio.save(reservaFromDb);

        ReservaResponse reservaResponse = composeReservaResponse(reservaActualizada);

        try {
            emailClient.sendModificationEmail(reservaResponse);
        } catch (Exception e) {
            LOG.warn("No se pudo enviar el correo de confirmación para la reserva {}: {}", reservaActualizada.getReservaId(), e.getMessage());
        }

        return reservaResponse;
    }

    @Override
    @Transactional
    public ReservaResponse actualizarReserva(Long reservaId, ReservaUpdateRequest reservaUpdateRequest) {

        if (reservaUpdateRequest.getNuevaFecha() != null && 
            reservaUpdateRequest.getNuevaFecha().isBefore(LocalDateTime.now())) {
            throw new ReservaFechaPasadaException("La fecha de la reserva no puede ser anterior a la fecha de hoy");
        }

        Reserva reservaFromDb = reservaRepositorio.findById(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + reservaId));
                
        if(reservaFromDb.getReservaStatus() == ReservaStatus.FACTURADA){
            throw new ReservaAlreadyFacturadaException("La RESERVA con ID ["+reservaId+"] ya ha sido facturada y no se puede modificar.");
        }

        LocalDateTime nuevaFecha = reservaUpdateRequest.getNuevaFecha() != null
                ? reservaUpdateRequest.getNuevaFecha()
                : reservaFromDb.getReservaFecha();

        LocalDateTime nuevaFechaFin = nuevaFecha.plus(DURACION_RESERVA);

        Integer nuevoNumeroPersonas = reservaUpdateRequest.getNuevoNumeroPersonas() != null
                ? reservaUpdateRequest.getNuevoNumeroPersonas()
                : reservaFromDb.getNumeroPersonas();

        // NOTA: SOLO SE BUSCA UNA NUEVA MESA SI CAMBIA EL NÚMERO DE PERSONAS
        if (reservaUpdateRequest.getNuevoNumeroPersonas() != null &&
            !nuevoNumeroPersonas.equals(reservaFromDb.getNumeroPersonas())) {

            List<Mesa> mesasDisponibles = mesaRepositorio.findMesasDisponibles(
                nuevoNumeroPersonas,
                nuevaFecha,
                nuevaFechaFin
            );

            if (mesasDisponibles.isEmpty()) {
                throw new NoTablesAvailableException("NO HAY MESAS DISPONIBLES PARA LA NUEVA FECHA Y NÚMERO DE PERSONAS");
            }

            reservaFromDb.setMesa(mesasDisponibles.get(0));

        } else if (reservaUpdateRequest.getNuevaFecha() != null) {
            // NOTA 2: SI EL NÚMERO DE PERSONAS NO CAMBIA, SOLO SE VERIFICA QUE LA MISMA MESA SIGUE LIBRE
            boolean mesaDisponible = reservaRepositorio.isMesaDisponibleEnRango(
                reservaFromDb.getMesa().getMesaId(),
                nuevaFecha,
                nuevaFechaFin,
                reservaFromDb.getReservaId()
            );

            if (!mesaDisponible) {
                throw new NoTablesAvailableException("LA MESA ACTUAL NO ESTÁ DISPONIBLE EN LA NUEVA FRANJA HORARIA");
            }
        }

        if (reservaUpdateRequest.getNuevaFecha() != null) {
            reservaFromDb.setReservaFecha(nuevaFecha);
            reservaFromDb.setReservaFin(nuevaFechaFin);
        }

        if (reservaUpdateRequest.getNuevoNumeroPersonas() != null) {
            reservaFromDb.setNumeroPersonas(nuevoNumeroPersonas);
        }

        if (reservaUpdateRequest.getNuevosAlergenos() != null) {
            if (reservaUpdateRequest.getNuevosAlergenos().isEmpty()) {
                reservaFromDb.getAlergenos().clear();
            } else {
                List<Alergeno> nuevosAlergenos = alergenoRepositorio.findAllById(reservaUpdateRequest.getNuevosAlergenos());
                reservaFromDb.setAlergenos(nuevosAlergenos);
            }
        }

        Reserva reservaActualizada = reservaRepositorio.save(reservaFromDb);

        ReservaResponse reservaResponse = composeReservaResponse(reservaActualizada);

        try {
            emailClient.sendModificationEmail(reservaResponse);
        } catch (Exception e) {
            LOG.warn("No se pudo enviar el correo de confirmación para la reserva {}: {}", reservaActualizada.getReservaId(), e.getMessage());
        }

        return reservaResponse;
    }

    @Override
    public ReservaResponse cancelarReservaByClient(Long id, ReservaClientRequest reservaCancelRequest) {
        Reserva reserva = reservaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + id));

        if (!reserva.getCliente().getEmail().equals(reservaCancelRequest.getClienteEmail())) {
            throw new IllegalArgumentException("El email proporcionado no coincide con el de la reserva");
        }

        return cancelarReserva(reserva);
    }

    @Override
    public ReservaResponse cancelarReservaByAdmin(Long id) {
        Reserva reserva = reservaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + id));
        
        return cancelarReserva(reserva);
    }

    private ReservaResponse cancelarReserva(Reserva reserva) {
        if (reserva.getReservaStatus() == ReservaStatus.CANCELADA || reserva.getReservaStatus() == ReservaStatus.FACTURADA) {
            throw new IllegalStateException("La reserva ya se encuentra CANCELADA o FACTURADA");
        }

        reserva.setReservaStatus(ReservaStatus.CANCELADA);
        Reserva reservaActualizada = reservaRepositorio.save(reserva);

        ReservaResponse response = composeReservaResponse(reservaActualizada);

        try {
            emailClient.sendCancelationEmail(response);
        } catch (Exception e) {
            LOG.warn("No se pudo enviar el correo de confirmación para la reserva {}: {}", reserva.getReservaId(), e.getMessage());
        }

        return response;
    }

    @Scheduled(fixedRate = 5 * 60 * 1000)
    @Transactional
    public void marcarReservasExpiradas(){
        LocalDateTime now = LocalDateTime.now();
        List<Reserva> reservasExpiradas = reservaRepositorio.findByReservaStatusAndReservaFinBefore(ReservaStatus.ACTIVA, now);

        if (!reservasExpiradas.isEmpty()) {
            LOG.info("Marcando {} reservas como EXPIRADAS", reservasExpiradas.size());
        } else {
            LOG.info("Ninguna RESERVA ha expirado, de momento");
        }
 
        for (Reserva reserva : reservasExpiradas) {
            reserva.setReservaStatus(ReservaStatus.EXPIRADA);
        }
    }

    private ReservaResponse composeReservaResponse(Reserva reservaGuardada) {
        ReservaResponse reservaResponse = new ReservaResponse();
        reservaResponse.setReservaId(reservaGuardada.getReservaId());
        reservaResponse.setReservaFecha(reservaGuardada.getReservaFecha());
        reservaResponse.setReservaFin(reservaGuardada.getReservaFin());
        reservaResponse.setNumeroPersonas(reservaGuardada.getNumeroPersonas());
        reservaResponse.setReservaStatus(reservaGuardada.getReservaStatus().name());

        Mesa mesa = reservaGuardada.getMesa();
        reservaResponse.setMesaId(mesa.getMesaId());
        reservaResponse.setMesaNumero(mesa.getMesaNumero());
        reservaResponse.setMesaCapacidad(mesa.getMesaCapacidad());

        Cliente cliente = reservaGuardada.getCliente();
        reservaResponse.setClienteNombre(cliente.getClienteNombre());
        reservaResponse.setClienteEmail(cliente.getEmail());

        if(reservaGuardada.getAlergenos() != null){
            List<String> alergenosNombres = reservaGuardada.getAlergenos().stream()
                .map(Alergeno::getNombreAlergeno)
                .collect(Collectors.toList());
            reservaResponse.setAlergenos(alergenosNombres);
        }

        return reservaResponse;
    }

}