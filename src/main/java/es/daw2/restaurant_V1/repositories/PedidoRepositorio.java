package es.daw2.restaurant_V1.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.daw2.restaurant_V1.models.Pedido;

@Repository
public interface PedidoRepositorio extends CrudRepository<Pedido, Long>{

}
