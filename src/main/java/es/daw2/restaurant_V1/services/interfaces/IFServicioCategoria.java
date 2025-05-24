package es.daw2.restaurant_V1.services.interfaces;

import java.util.ArrayList;
import java.util.Optional;

import es.daw2.restaurant_V1.dtos.categoria.CategoriaRequest;
import es.daw2.restaurant_V1.dtos.categoria.CategoriaResponse;
import es.daw2.restaurant_V1.models.Categoria;

public interface IFServicioCategoria {

    // Métodos a implementar
    public abstract ArrayList<Categoria> getCategories();
    public abstract Optional<Categoria> getCategoryById(Long id);
    public abstract boolean addCategory(Categoria category);
    public abstract boolean updateCategory(Categoria category, Long id);
    public abstract boolean deleteCategory(Long id);
    //Método nuevos
    public abstract CategoriaResponse crearCategoria(CategoriaRequest categoriaRequest);
    public abstract CategoriaResponse actualizarCategoria(Long categoriaId, CategoriaRequest categoriaRequest);
}
