package es.daw2.restaurant_V1.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.models.Reserva;
import es.daw2.restaurant_V1.repositories.ReservaRepositorio;

@Service
public class ImpServicioReserva implements IFServicioReserva{

    @Autowired
    ReservaRepositorio reservationRepository;

    @Override
    public ArrayList<Reserva> getReservations() {
        return (ArrayList<Reserva>) reservationRepository.findAll();
    }

    @Override
    public Optional<Reserva> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    public boolean addReservation(Reserva reservation) {
        if(reservation != null){
            reservationRepository.save(reservation);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateReservation(Reserva reservation, Long id) {
        Optional<Reserva> reservationContainer = reservationRepository.findById(id);

        if(reservationContainer.isPresent()){
            Reserva existingReservation = reservationContainer.get();
            existingReservation.setNumero_personas(reservation.getNumero_personas());
            existingReservation.setReserva_fecha(reservation.getReserva_fecha());
            reservationRepository.save(existingReservation);

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteReservation(Long id) {
        Optional<Reserva> reservationContainer = reservationRepository.findById(id);

        if(reservationContainer.isPresent()){
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
