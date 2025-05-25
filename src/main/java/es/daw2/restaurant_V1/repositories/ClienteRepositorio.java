package es.daw2.restaurant_V1.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Cliente;

@Repository
public interface ClienteRepositorio extends JpaRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
}
