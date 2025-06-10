package es.daw2.restaurant_V1.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.reservas.ReservaClientRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaResponse;
import es.daw2.restaurant_V1.dtos.reservas.ReservaUpdateClientRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaUpdateRequest;

public interface IFServicioReserva {

    public abstract ReservaResponse crearReserva (ReservaRequest reservaRequest);
    public abstract ReservaResponse actualizarReserva (Long reservaId, ReservaUpdateRequest reservaUpdateRequest);
    public abstract ReservaResponse actualizarReservaByClient(Long id, ReservaUpdateClientRequest reservaUpdateRequest);
    public abstract Page<ReservaResponse> getTodayReservas(Pageable pageable);
    public abstract Page<ReservaResponse> getAllReservas(Pageable pageable);
    public abstract ReservaResponse findReservaById(Long id);
    public ReservaResponse findReservaByIdByClient(ReservaClientRequest reservaClientRequest, Long id);
    public ReservaResponse cancelarReservaByClient (Long id, ReservaClientRequest reservaCancelRequest);
    public ReservaResponse cancelarReservaByAdmin (Long id);
}