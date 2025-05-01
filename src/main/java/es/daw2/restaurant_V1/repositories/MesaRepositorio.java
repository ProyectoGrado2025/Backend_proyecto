package es.daw2.restaurant_V1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Mesa;

@Repository
public interface MesaRepositorio extends CrudRepository<Mesa, Long> {

}
