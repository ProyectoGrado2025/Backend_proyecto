package es.daw2.restaurant_V1.services;

import java.util.ArrayList;
import java.util.Optional;

import es.daw2.restaurant_V1.models.PlatoCategoria;

public interface IFServicioPlatoCategoria {

    // Métodos a implementar
    public abstract ArrayList<PlatoCategoria> getCategories();
    public abstract Optional<PlatoCategoria> getCategoryById(Long id);
    public abstract boolean addCategory(PlatoCategoria category);
    public abstract boolean updateCategory(PlatoCategoria category, Long id);
    public abstract boolean deleteCategory(Long id);
}
