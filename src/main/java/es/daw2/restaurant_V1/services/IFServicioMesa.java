package es.daw2.restaurant_V1.services;

import java.util.ArrayList;
import java.util.Optional;

import es.daw2.restaurant_V1.models.Mesa;


public interface IFServicioMesa {

    // Métodos a implementar
    public abstract ArrayList<Mesa> getMesas();
    public abstract Optional<Mesa> getMesaById(Long id);
    public abstract boolean addMesa(Mesa table);
    public abstract boolean updateMesa(Mesa table, Long id);
    public abstract boolean deleteMesa(Long id);

}
