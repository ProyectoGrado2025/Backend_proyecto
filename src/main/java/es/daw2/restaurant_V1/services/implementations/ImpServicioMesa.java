package es.daw2.restaurant_V1.services.implementations;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.mesas.MesaRequest;
import es.daw2.restaurant_V1.dtos.mesas.MesaResponse;
import es.daw2.restaurant_V1.models.Mesa;
import es.daw2.restaurant_V1.models.Mesa.MesaStatus;
import es.daw2.restaurant_V1.repositories.MesaRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioMesa;
import lombok.NonNull;

@Service
public class ImpServicioMesa implements IFServicioMesa{

    @Autowired
    MesaRepositorio mesaRepository;

    @Override
    public ArrayList<Mesa> getMesas() {
        return (ArrayList<Mesa>) mesaRepository.findAll();
    }

    @Override
    public Optional<Mesa> getMesaById(Long id) {
        return mesaRepository.findById(id);
    }

    /*
     * ACTUALIZADO 19/05/2025
     */
    @Override
    public MesaResponse addMesa(@NonNull MesaRequest mesaRequest) {

        Mesa mesaToAdd = new Mesa();
        mesaToAdd.setMesaNumero(mesaRequest.getMesaNumero());
        mesaToAdd.setMesaCapacidad(mesaRequest.getMesaCapacidad());
        mesaToAdd.setMesaDisponibilidad(MesaStatus.DISPONIBLE);

        Mesa mesaGuardada = mesaRepository.save(mesaToAdd);

        MesaResponse mesaResponse = new MesaResponse();
        mesaResponse.setMesaId(mesaGuardada.getMesaId());
        mesaResponse.setMesaCapacidad(mesaGuardada.getMesaCapacidad());
        mesaResponse.setMesaNumero(mesaGuardada.getMesaNumero());
        mesaResponse.setMesaStatus(mesaGuardada.getMesaDisponibilidad().name());

        return mesaResponse;
    }

    @Override
    public boolean updateMesa(Mesa table, Long id) {
        Optional<Mesa> mesaContainer = mesaRepository.findById(id);

        if(mesaContainer.isPresent()){
            Mesa existingMesa = mesaContainer.get();

            existingMesa.setMesaCapacidad(table.getMesaCapacidad());
            existingMesa.setMesaDisponibilidad(table.getMesaDisponibilidad());
            existingMesa.setMesaNumero(table.getMesaNumero());

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteMesa(Long id) {
        Optional<Mesa> mesaContainer = mesaRepository.findById(id);

        if(mesaContainer.isPresent()){
            mesaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
