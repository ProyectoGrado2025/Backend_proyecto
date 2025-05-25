package es.daw2.restaurant_V1.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.categoria.CategoriaRequest;
import es.daw2.restaurant_V1.dtos.categoria.CategoriaResponse;

public interface IFServicioCategoria {

    public abstract Page<CategoriaResponse> getAllCategorias (Pageable pageable);
    public abstract CategoriaResponse getCategoriaById(Long id);
    public abstract CategoriaResponse crearCategoria(CategoriaRequest categoriaRequest);
    public abstract CategoriaResponse actualizarCategoria(Long categoriaId, CategoriaRequest categoriaRequest);
}
