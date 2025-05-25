package es.daw2.restaurant_V1.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.pedidos.PedidoRequest;
import es.daw2.restaurant_V1.dtos.pedidos.PedidoResponse;

public interface IFServicioPedido {

    public abstract Page<PedidoResponse> getAllPedidos(Pageable pageable);
    public abstract PedidoResponse getPedidoById(Long id);
    public abstract PedidoResponse crearPedido (PedidoRequest pedidoRequest);
}
