package es.daw2.restaurant_V1.dtos.pedidos;

import java.io.Serializable;
import java.util.List;

import es.daw2.restaurant_V1.dtos.LineasPedido.LineaPedidoRequest;
import lombok.Data;

@Data
public class PedidoRequest implements Serializable{

    private Long reservaId;
    private List<LineaPedidoRequest> lineasPedido;
}
