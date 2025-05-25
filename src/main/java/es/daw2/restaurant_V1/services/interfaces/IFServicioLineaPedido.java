package es.daw2.restaurant_V1.services.interfaces;

import java.util.List;

import es.daw2.restaurant_V1.dtos.LineasPedido.LineaPedidoRequest;
import es.daw2.restaurant_V1.dtos.LineasPedido.LineaPedidoResponse;
import es.daw2.restaurant_V1.models.LineaPedido;
import es.daw2.restaurant_V1.models.Pedido;

public interface IFServicioLineaPedido {

    public abstract List<LineaPedido> crearLineaPedido (List<LineaPedidoRequest> lineaPedidoRequest, Pedido pedido);
    public abstract List<LineaPedidoResponse> crearLineaPedidoResponse (List<LineaPedido> lineaPedido);
}
