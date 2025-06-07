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
import java.util.stream.Collectors;

import es.daw2.restaurant_V1.dtos.stadistics.reservas.ContadorReservasPorEstadoDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservasPorDiaDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservasPorMesDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservasPorSemanaDTO;
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

    @Query("""
        SELECT AVG(r.numeroPersonas)
        FROM Reserva r        
    """)
    Double avgPersonasPorReserva();

    @Query("""
        SELECT new es.daw2.restaurant_V1.dtos.stadistics.reservas.ContadorReservasPorEstadoDTO(
            r.reservaStatus, COUNT(r))
        FROM Reserva r
        GROUP BY r.reservaStatus
    """)
    List<ContadorReservasPorEstadoDTO> contarReservasPorEstado();

    @Query("""
        SELECT CAST(r.reservaFecha AS date), COUNT(r)
        FROM Reserva r
        WHERE r.reservaFecha BETWEEN :fechaInicio AND :fechaFin
        GROUP BY CAST(r.reservaFecha AS date)
        ORDER BY CAST(r.reservaFecha AS date)
    """)
    List<Object[]> contarReservasPorDiaRaw(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin
    );

    @Query("""
        SELECT FUNCTION('YEAR', r.reservaFecha), FUNCTION('WEEK', r.reservaFecha), COUNT(r)
        FROM Reserva r
        WHERE r.reservaFecha BETWEEN :fechaInicio AND :fechaFin
        GROUP BY FUNCTION('YEAR', r.reservaFecha), FUNCTION('WEEK', r.reservaFecha)
        ORDER BY FUNCTION('YEAR', r.reservaFecha), FUNCTION('WEEK', r.reservaFecha)
    """)
    List<Object[]> contarReservasPorSemanaRaw(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin
    );

    @Query("""
        SELECT FUNCTION('YEAR', r.reservaFecha), FUNCTION('MONTH', r.reservaFecha), COUNT(r)
        FROM Reserva r
        WHERE r.reservaFecha BETWEEN :fechaInicio AND :fechaFin
        GROUP BY FUNCTION('YEAR', r.reservaFecha), FUNCTION('MONTH', r.reservaFecha)
        ORDER BY FUNCTION('YEAR', r.reservaFecha), FUNCTION('MONTH', r.reservaFecha)
    """)
    List<Object[]> contarReservasPorMesRaw(
        @Param("fechaInicio") LocalDateTime fechaInicio,
        @Param("fechaFin") LocalDateTime fechaFin
    );
    
    default List<ReservasPorDiaDTO> contarReservasPorDia(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return contarReservasPorDiaRaw(fechaInicio, fechaFin).stream()
            .map(row -> new ReservasPorDiaDTO(
                ((java.sql.Date) row[0]).toLocalDate(),
                ((Number) row[1]).longValue()
            ))
            .collect(Collectors.toList());
    }

    default List<ReservasPorSemanaDTO> contarReservasPorSemana(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return contarReservasPorSemanaRaw(fechaInicio, fechaFin).stream()
            .map(row -> new ReservasPorSemanaDTO(
                ((Number) row[0]).intValue(),
                ((Number) row[1]).intValue(),
                ((Number) row[2]).longValue()
            ))
            .collect(Collectors.toList());
    }

    default List<ReservasPorMesDTO> contarReservasPorMes(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return contarReservasPorMesRaw(fechaInicio, fechaFin).stream()
            .map(row -> new ReservasPorMesDTO(
                ((Number) row[0]).intValue(),
                ((Number) row[1]).intValue(),
                ((Number) row[2]).longValue()
            ))
            .collect(Collectors.toList());
    }
}