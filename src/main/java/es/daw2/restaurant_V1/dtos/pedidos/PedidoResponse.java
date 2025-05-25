package es.daw2.restaurant_V1.dtos.pedidos;

import java.io.Serializable;
import java.util.List;

import es.daw2.restaurant_V1.dtos.LineasPedido.LineaPedidoResponse;
import lombok.Data;

@Data
public class PedidoResponse implements Serializable{

    private Long pedidoId;
    private List<LineaPedidoResponse> lineasPedidoResponse;
}