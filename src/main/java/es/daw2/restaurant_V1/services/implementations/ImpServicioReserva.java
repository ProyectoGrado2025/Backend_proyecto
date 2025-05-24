package es.daw2.restaurant_V1.services.implementations;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.reservas.ReservaRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaResponse;
import es.daw2.restaurant_V1.dtos.reservas.ReservaUpdateRequest;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.exceptions.custom.NoTablesAvailableException;
import es.daw2.restaurant_V1.models.Alergeno;
import es.daw2.restaurant_V1.models.Cliente;
import es.daw2.restaurant_V1.models.Mesa;
import es.daw2.restaurant_V1.models.Reserva;
import es.daw2.restaurant_V1.repositories.AlergenoRepositorio;
import es.daw2.restaurant_V1.repositories.ClienteRepositorio;
import es.daw2.restaurant_V1.repositories.MesaRepositorio;
import es.daw2.restaurant_V1.repositories.ReservaRepositorio;
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

    private static final Duration DURACION_RESERVA = Duration.ofHours(2);

    @Override
    public ArrayList<Reserva> getReservations() {
        return (ArrayList<Reserva>) reservaRepositorio.findAll();
    }

    @Override
    public Optional<Reserva> getReservationById(Long id) {
        return reservaRepositorio.findById(id);
    }

    @Override
    public boolean addReservation(Reserva reservation) {
        if(reservation != null){
            reservaRepositorio.save(reservation);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReservation(Reserva reservation, Long id) {
        Optional<Reserva> reservationContainer = reservaRepositorio.findById(id);

        if(reservationContainer.isPresent()){
            Reserva existingReservation = reservationContainer.get();
            existingReservation.setNumeroPersonas(reservation.getNumeroPersonas());
            existingReservation.setReservaFecha(reservation.getReservaFecha());
            reservaRepositorio.save(existingReservation);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReservation(Long id) {
        Optional<Reserva> reservationContainer = reservaRepositorio.findById(id);

        if(reservationContainer.isPresent()){
            reservaRepositorio.deleteById(id);
            return true;
        }
        return false;
    }

    /**
     * NUEVAS IMPLEMENTACIONES
     * MEJORES IMPLEMENTACIONES
     */
    @Override
    public ReservaResponse crearReserva (ReservaRequest reservaRequest){
        Cliente clienteFromDb = clienteRepositorio.findByEmail(reservaRequest.getClienteEmail())
            .orElseGet( ()-> {
                    Cliente nuevoCliente = new Cliente();
                    nuevoCliente.setClienteNombre(reservaRequest.getClienteNombre());
                    nuevoCliente.setEmail(reservaRequest.getClienteEmail());
                    nuevoCliente.setClienteTlfn(reservaRequest.getClienteTlfn());

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

        return createReservaResponse(reservaGuardada);
    }

    private ReservaResponse createReservaResponse(Reserva reservaGuardada) {
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

    @Override
    @Transactional
    public ReservaResponse actualizarReserva(Long reservaId, ReservaUpdateRequest reservaUpdateRequest) {
        Reserva reservaFromDb = reservaRepositorio.findById(reservaId)
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + reservaId));

        LocalDateTime nuevaFecha = reservaUpdateRequest.getNuevaFecha();
        Integer nuevoNumeroPersonas = reservaUpdateRequest.getNuevoNumeroPersonas();
        LocalDateTime nuevaFechaFin = nuevaFecha.plus(DURACION_RESERVA);

        // NOTA: SOLO SE BUSCA UNA NUEVA MESA SI CAMBIA EL NÚMERO DE PERSONAS
        if(!nuevoNumeroPersonas.equals(reservaFromDb.getNumeroPersonas())){

            List<Mesa> mesasDisponibles = mesaRepositorio.findMesasDisponibles(
                nuevoNumeroPersonas,
                nuevaFecha,
                nuevaFechaFin
            );

            if(mesasDisponibles.isEmpty()){
                throw new NoTablesAvailableException("NO HAY MESAS DISPONIBLES PARA LA NUEVA FECHA Y NÚMERO DE PERSONAS");
            }

            reservaFromDb.setMesa(mesasDisponibles.get(0));

        // NOTA 2: SI EL NÚMERO DE PERSONAS NO CAMBIA, SOLO SE VERIFICA QUE LA MISMA MESA SIGUE LIBRE
        } else {

            boolean mesaDisponible = reservaRepositorio.isMesaDisponibleEnRango(
                reservaFromDb.getMesa().getMesaId(),
                nuevaFecha,
                nuevaFechaFin,
                reservaFromDb.getReservaId()
            );

            if(!mesaDisponible){
                throw new NoTablesAvailableException("LA MESA ACTUAL NO ESTÁ DISPONIBLE EN LA NUEVA FRANJA HORARIA");
            }
        }

        reservaFromDb.setReservaFecha(nuevaFecha);
        reservaFromDb.setReservaFin(nuevaFechaFin);
        reservaFromDb.setNumeroPersonas(nuevoNumeroPersonas);

        if(reservaUpdateRequest.getNuevosAlergenos() != null){
            if(reservaUpdateRequest.getNuevosAlergenos().isEmpty()){
                reservaFromDb.getAlergenos().clear();
            } else {
                List<Alergeno> nuevosAlergenos = alergenoRepositorio.findAllById(reservaUpdateRequest.getNuevosAlergenos());
                reservaFromDb.setAlergenos(nuevosAlergenos);
            }
        }

        Reserva reservaActualizada = reservaRepositorio.save(reservaFromDb);

        return createReservaResponse(reservaActualizada);
    }
}