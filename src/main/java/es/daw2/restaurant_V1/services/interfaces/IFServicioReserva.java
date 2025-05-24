package es.daw2.restaurant_V1.services.interfaces;

import java.util.ArrayList;
import java.util.Optional;

import es.daw2.restaurant_V1.dtos.reservas.ReservaRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaResponse;
import es.daw2.restaurant_V1.dtos.reservas.ReservaUpdateRequest;
import es.daw2.restaurant_V1.models.Reserva;

public interface IFServicioReserva {

    // Métodos a implementar
    public abstract ArrayList<Reserva> getReservations();
    public abstract Optional<Reserva> getReservationById(Long id);
    public abstract boolean addReservation(Reserva reservation);
    public abstract boolean updateReservation(Reserva reservation, Long id);
    public abstract boolean deleteReservation(Long id);
    /**
     * MÉTODOS PARA IMPLEMENTAR - ACTUALIZADOS
     */
    public abstract ReservaResponse crearReserva (ReservaRequest reservaRequest);
    public abstract ReservaResponse actualizarReserva (Long reservaId, ReservaUpdateRequest reservaUpdateRequest);
}
