package es.daw2.restaurant_V1.services.implementations;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.models.Pedido;
import es.daw2.restaurant_V1.repositories.PedidoRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioPedido;

@Service
public class ImpServicioPedido implements IFServicioPedido{

    @Autowired
    PedidoRepositorio orderRepository;

    @Override
    public ArrayList<Pedido> getOrders() {
        return (ArrayList<Pedido>) orderRepository.findAll();
    }

    @Override
    public Optional<Pedido> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    @Override
    public boolean createOrder(Pedido order) {
        if(order != null){
            orderRepository.save(order);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateOrder(Pedido order, Long id) {
        // Optional<Pedido> orderContainer = orderRepository.findById(id);

        // if(orderContainer.isPresent()){
        //     Pedido existingOrder = orderContainer.get();
        //     //existingOrder.setPlatos(order.getPlatos());

        //     return true;
        // }
        // return false;
        return true;
    }

    @Override
    public boolean deleteOrder(Long id) {
        Optional<Pedido> orderContainer = orderRepository.findById(id);

        if(orderContainer.isPresent()){
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
