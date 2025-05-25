package es.daw2.restaurant_V1.repositories;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Mesa;

@Repository
public interface MesaRepositorio extends JpaRepository<Mesa, Long> {

    @Query("""
        SELECT m 
        FROM Mesa m
        WHERE m.mesaDisponibilidad = es.daw2.restaurant_V1.models.Mesa.MesaStatus.DISPONIBLE
        AND m.mesaCapacidad BETWEEN :numeroPersonas AND (:numeroPersonas + 2)
        AND m.mesaId NOT IN (
            SELECT r.mesa.mesaId 
            FROM Reserva r
            WHERE r.reservaStatus = es.daw2.restaurant_V1.models.Reserva.ReservaStatus.ACTIVA
                AND (:reservaFecha < r.reservaFin AND :reservaFin > r.reservaFecha)
        )
    """)
    List<Mesa> findMesasDisponibles(
        @Param("numeroPersonas") Integer numeroPersonas,
        @Param("reservaFecha") LocalDateTime reservaFecha,
        @Param("reservaFin") LocalDateTime reservaFin
    );

    Optional<Mesa> findByMesaNumero(Integer mesaNumero);
}