package es.daw2.restaurant_V1.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.platos.PlatoRequest;
import es.daw2.restaurant_V1.dtos.platos.PlatoResponse;

public interface IFServicioPlato {
    /*
     * NUEVAS IMPLEMENTACIONES
     */
    public abstract Page<PlatoResponse> getAllPlatos(Pageable pageable);
    public abstract PlatoResponse getPlatoById(Long id);
    public abstract PlatoResponse crearPlato (PlatoRequest platoRequest);
    public abstract PlatoResponse actualizarPlato (Long id, PlatoRequest platoRequest);
}