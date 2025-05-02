package es.daw2.restaurant_V1.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.models.Mesa;
import es.daw2.restaurant_V1.repositories.MesaRepositorio;

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

    @Override
    public boolean addMesa(Mesa table) {
        if (table != null){
            mesaRepository.save(table);

            return true;
        }
        return false;
    }

    @Override
    public boolean updateMesa(Mesa table, Long id) {
        Optional<Mesa> mesaContainer = mesaRepository.findById(id);

        if(mesaContainer.isPresent()){
            Mesa existingMesa = mesaContainer.get();

            existingMesa.setMesa_capacidad(table.getMesa_capacidad());
            existingMesa.setMesa_disponibilidad(table.getMesa_disponibilidad());
            existingMesa.setMesa_numero(table.getMesa_numero());

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
