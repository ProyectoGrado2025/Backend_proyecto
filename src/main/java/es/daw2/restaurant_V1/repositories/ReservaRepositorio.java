package es.daw2.restaurant_V1.repositories;

import java.time.LocalDateTime;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Reserva;

import java.util.List;

import es.daw2.restaurant_V1.models.Mesa;


@Repository
public interface ReservaRepositorio extends JpaRepository<Reserva, Long>{
    
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

    List<Reserva> findByMesa(Mesa mesa);

    List<Reserva> findByReservaStatusAndReservaFinBefore(Reserva.ReservaStatus status, LocalDateTime fechaHoraLimite);

    @Query("""
        SELECT r 
        FROM Reserva r 
        WHERE r.reservaFecha >= :startOfDay AND r.reservaFecha < :endOfDay
    """)
    Page<Reserva> findByReservaFechaBetween(
        @Param("startOfDay") LocalDateTime startOfDay,
        @Param("endOfDay") LocalDateTime endOfDay,
        Pageable pageable
    );

}