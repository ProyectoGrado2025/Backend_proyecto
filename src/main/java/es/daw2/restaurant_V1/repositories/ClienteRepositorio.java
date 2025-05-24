package es.daw2.restaurant_V1.repositories;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Cliente;

@Repository
public interface ClienteRepositorio extends CrudRepository<Cliente, Long> {

    Optional<Cliente> findByEmail(String email);
}
