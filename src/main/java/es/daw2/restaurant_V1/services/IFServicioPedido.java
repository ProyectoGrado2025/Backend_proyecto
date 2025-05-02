package es.daw2.restaurant_V1.services;

import java.util.ArrayList;
import java.util.Optional;

import es.daw2.restaurant_V1.models.Pedido;

public interface IFServicioPedido {

    // Métodos a implementar
    public abstract ArrayList<Pedido> getOrders();
    public abstract Optional<Pedido> getOrderById(Long id);
    public abstract boolean createOrder(Pedido order);
    public abstract boolean updateOrder(Pedido order, Long id);
    public abstract boolean deleteOrder(Long id);

}
