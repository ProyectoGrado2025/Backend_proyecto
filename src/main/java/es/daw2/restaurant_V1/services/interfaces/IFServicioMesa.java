package es.daw2.restaurant_V1.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.mesas.MesaRequest;
import es.daw2.restaurant_V1.dtos.mesas.MesaResponse;
import es.daw2.restaurant_V1.dtos.mesas.MesaUpdateRequest;

public interface IFServicioMesa {

    // Métodos a implementar
    public abstract Page<MesaResponse> getAllMesas(Pageable pageable) ;
    public abstract MesaResponse getMesaById(Long id);
    public abstract MesaResponse crearMesa(MesaRequest table);
    public abstract MesaResponse intercambiarNumeroMesa(Long id, MesaUpdateRequest mesaUpdateRequest);
    public MesaResponse actualizarMesaStatus(Long id, MesaUpdateRequest mesaUpdateRequest);

}
