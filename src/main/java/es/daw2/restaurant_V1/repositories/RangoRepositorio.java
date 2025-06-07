package es.daw2.restaurant_V1.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Rango;

@Repository
public interface RangoRepositorio extends JpaRepository<Rango, Long>{

    Optional<Rango> getRangoByPuntosMinimos(Long puntosMinimos);
    List<Rango> findAllByOrderByPuntosMinimosDesc();
}
