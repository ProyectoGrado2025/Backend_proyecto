package es.daw2.restaurant_V1.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Beneficio;

@Repository
public interface BeneficioRepositorio extends JpaRepository<Beneficio, Long>{

    List<Beneficio> findAllByStatus(Beneficio.BeneficioStatus status);
}
