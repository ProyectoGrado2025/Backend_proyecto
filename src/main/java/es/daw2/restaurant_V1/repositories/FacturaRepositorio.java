package es.daw2.restaurant_V1.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Factura;

@Repository
public interface FacturaRepositorio extends JpaRepository<Factura, Long>{

}
