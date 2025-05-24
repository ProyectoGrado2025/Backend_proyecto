package es.daw2.restaurant_V1.services.interfaces;

import java.util.ArrayList;
import java.util.Optional;

import es.daw2.restaurant_V1.models.Plato;

public interface IFServicioPlato {

    // Métodos a implementar
    public abstract ArrayList<Plato> getDishes();
    public abstract Optional<Plato> getDishById(Long id);
    public abstract boolean addDish(Plato dish);
    public abstract boolean updateDish(Plato dish, Long id);
    public abstract boolean deleteDish(Long id);
}
