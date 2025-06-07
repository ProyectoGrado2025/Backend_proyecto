package es.daw2.restaurant_V1.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.LineasPedido.LineaPedidoResponse;
import es.daw2.restaurant_V1.dtos.pedidos.PedidoRequest;
import es.daw2.restaurant_V1.dtos.pedidos.PedidoResponse;
import es.daw2.restaurant_V1.models.LineaPedido;
import es.daw2.restaurant_V1.models.Pedido;
import es.daw2.restaurant_V1.models.Reserva;
import es.daw2.restaurant_V1.repositories.PedidoRepositorio;
import es.daw2.restaurant_V1.repositories.ReservaRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioLineaPedido;
import es.daw2.restaurant_V1.services.interfaces.IFServicioPedido;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ImpServicioPedido implements IFServicioPedido{

    @Autowired
    PedidoRepositorio pedidoRepositorio;
    @Autowired
    ReservaRepositorio reservaRepositorio;
    @Autowired
    IFServicioLineaPedido servicioLineaPedido;


    @Override
    public Page<PedidoResponse> getAllPedidos(Pageable pageable) {
        return pedidoRepositorio.findAll(pageable)
                .map(this::composePedidoResponse);
    }

    @Override
    public PedidoResponse getPedidoById(Long id) {
        Pedido pedido = pedidoRepositorio.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Pedido no encontrado con ID: "+id));
        return composePedidoResponse(pedido);
    }

    @Override
    @Transactional
    public PedidoResponse crearPedido(PedidoRequest pedidoRequest) {
        // se busca la reserva asociada
        Reserva reservaFromDb = reservaRepositorio.findById(pedidoRequest.getReservaId())
                .orElseThrow(() -> new EntityNotFoundException("Reserva no encontrada con ID: " + pedidoRequest.getReservaId()));

        if(reservaFromDb.getPedido() != null){
            throw new IllegalStateException("La reserva ya tiene un pedido.");
        }
        // se crea el  nuevo pedido y se le asigna la reserva
        Pedido pedido = new Pedido();
        pedido.setReserva(reservaFromDb);

        // se crean las  líneas de pedido y se asignan al pedido
        List<LineaPedido> lineasPedido = servicioLineaPedido.crearLineaPedido(pedidoRequest.getLineasPedido(), pedido);
        pedido.setLineasPedido(lineasPedido);

        // se guarda el pedido
        Pedido pedidoGuardado = pedidoRepositorio.saveAndFlush(pedido);

        return composePedidoResponse(pedidoGuardado);
    }

    private PedidoResponse composePedidoResponse(Pedido pedidoGuardado) {
        PedidoResponse pedidoResponse = new PedidoResponse();
        pedidoResponse.setPedidoId(pedidoGuardado.getPedidoId());

        // Se transforme las líneas de pedido a DTOs
        List<LineaPedidoResponse> lineaPedidoResponses = servicioLineaPedido.crearLineaPedidoResponse(pedidoGuardado.getLineasPedido());
        pedidoResponse.setLineasPedidoResponse(lineaPedidoResponses);

        return pedidoResponse;
    }
}