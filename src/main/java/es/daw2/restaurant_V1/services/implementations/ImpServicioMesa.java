package es.daw2.restaurant_V1.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.mesas.MesaRequest;
import es.daw2.restaurant_V1.dtos.mesas.MesaResponse;
import es.daw2.restaurant_V1.dtos.mesas.MesaUpdateRequest;
import es.daw2.restaurant_V1.models.Mesa;
import es.daw2.restaurant_V1.models.Reserva;
import es.daw2.restaurant_V1.models.Mesa.MesaStatus;
import es.daw2.restaurant_V1.repositories.MesaRepositorio;
import es.daw2.restaurant_V1.repositories.ReservaRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioMesa;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.NonNull;

@Service
public class ImpServicioMesa implements IFServicioMesa{

    @Autowired
    MesaRepositorio mesaRepositorio;
    @Autowired
    ReservaRepositorio reservaRepositorio;

    @Override
    public Page<MesaResponse> getAllMesas(Pageable pageable) {
        return mesaRepositorio.findAll(pageable)
                .map(this::composeMesaResponse);
    }

    @Override
    public MesaResponse getMesaById(Long id) {
        Mesa mesaFromDb = mesaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada con ID: " + id));

        return composeMesaResponse(mesaFromDb);
    }

    @Override
    public MesaResponse crearMesa(@NonNull MesaRequest mesaRequest) {

        Mesa mesaToAdd = new Mesa();
        mesaToAdd.setMesaNumero(mesaRequest.getMesaNumero());
        mesaToAdd.setMesaCapacidad(mesaRequest.getMesaCapacidad());
        mesaToAdd.setMesaDisponibilidad(MesaStatus.DISPONIBLE);

        Mesa mesaGuardada = mesaRepositorio.save(mesaToAdd);

        return composeMesaResponse(mesaGuardada);
    }

    @Override
    @Transactional
    public MesaResponse actualizarMesaStatus(Long id, MesaUpdateRequest mesaUpdateRequest){
        Mesa mesa = mesaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Mesa no encontrada con ID: " + id));

        mesa.setMesaDisponibilidad(mesaUpdateRequest.getMesaStatus());

        Mesa mesaActualizada = mesaRepositorio.save(mesa);

        return composeMesaResponse(mesaActualizada);
    }

    @Override
    @Transactional
    public MesaResponse intercambiarNumeroMesa(Long id, MesaUpdateRequest mesaUpdateRequest){
        Mesa mesaA = mesaRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Mesa no encontrada con ID: " + id));
        
        Mesa mesaB = mesaRepositorio.findByMesaNumero(mesaUpdateRequest.getNuevoMesaNumero())
                .orElseThrow(() -> new EntityNotFoundException("No existe ninguna mesa con el número: " + mesaUpdateRequest.getNuevoMesaNumero()));

        Integer numeroMesaA = mesaA.getMesaNumero();
        Integer numeroMesaB = mesaB.getMesaNumero();

        mesaA.setMesaNumero(-1);
        mesaRepositorio.save(mesaA);

        mesaB.setMesaNumero(numeroMesaA);
        mesaRepositorio.save(mesaB);

        mesaA.setMesaNumero(numeroMesaB);
        mesaRepositorio.save(mesaA);

        List<Reserva> reservasA = reservaRepositorio.findByMesa(mesaA);
        List<Reserva> reservasB = reservaRepositorio.findByMesa(mesaB);

        for (Reserva r : reservasA){
            r.setMesa(mesaA);
        }
        for(Reserva r : reservasB){
            r.setMesa(mesaB);
        }
        reservaRepositorio.saveAll(reservasA);
        reservaRepositorio.saveAll(reservasB);

        return composeMesaResponse(mesaA);
    }

    private MesaResponse composeMesaResponse(Mesa mesa) {
        MesaResponse response = new MesaResponse();
        response.setMesaId(mesa.getMesaId());
        response.setMesaNumero(mesa.getMesaNumero());
        response.setMesaCapacidad(mesa.getMesaCapacidad());
        response.setMesaStatus(mesa.getMesaDisponibilidad().name());
        return response;
    }
}
