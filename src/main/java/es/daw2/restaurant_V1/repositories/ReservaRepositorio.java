package es.daw2.restaurant_V1.repositories;

import java.time.LocalDateTime;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Reserva;

@Repository
public interface ReservaRepositorio extends CrudRepository<Reserva, Long>{
    
    @Query("""
        SELECT COUNT(r) = 0 
        FROM Reserva r
        WHERE r.mesa.mesaId = :mesaId
        AND r.reservaId <> :reservaId
        AND r.reservaStatus = 'ACTIVA'
        AND (:nuevaFecha < r.reservaFin AND :nuevaFechaFin > r.reservaFecha)
    """)
    boolean isMesaDisponibleEnRango(
        @Param("mesaId") Long mesaId,
        @Param("nuevaFecha") LocalDateTime nuevaFecha,
        @Param("nuevaFechaFin") LocalDateTime nuevaFechaFin,
        @Param("reservaId") Long reservaId
    );
}