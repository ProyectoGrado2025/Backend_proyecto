package es.daw2.restaurant_V1.services.implementations;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.LineasPedido.LineaPedidoRequest;
import es.daw2.restaurant_V1.dtos.LineasPedido.LineaPedidoResponse;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.models.LineaPedido;
import es.daw2.restaurant_V1.models.Pedido;
import es.daw2.restaurant_V1.models.Plato;
import es.daw2.restaurant_V1.repositories.LineaPedidoRepositorio;
import es.daw2.restaurant_V1.repositories.PedidoRepositorio;
import es.daw2.restaurant_V1.repositories.PlatoRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioLineaPedido;

@Service
public class ImpServicioLineaPedido implements IFServicioLineaPedido{

    @Autowired
    LineaPedidoRepositorio lineaPedidoRepositorio;
    @Autowired
    PlatoRepositorio platoRepositorio;
    @Autowired
    PedidoRepositorio pedidoRepositorio;

    @Override
    public List<LineaPedido> crearLineaPedido(List<LineaPedidoRequest> lineaPedidoRequestList, Pedido pedido) {
        List<LineaPedido> lineasPedido = lineaPedidoRequestList.stream()
            .map(request -> {
                LineaPedido linea = new LineaPedido();
                linea.setCantidad(request.getCantidadPlato());
                linea.setPedido(pedido);
                Plato plato = platoRepositorio.findById(request.getPlatoId())
                    .orElseThrow(() -> new EntityNotFoundException("Plato no encontrado"));
                linea.setPlato(plato);
                linea.setPrecioUnitario((BigDecimal.valueOf(plato.getPlatoPrecio().doubleValue())));
                return linea;
            })
            .collect(Collectors.toList());

        return lineasPedido;
    }

    @Override
    public List<LineaPedidoResponse> crearLineaPedidoResponse (List<LineaPedido> lineasPedido){
        List<LineaPedidoResponse> lineasPedidoResponse = lineasPedido.stream()
            .map(lineaPedido -> {
                LineaPedidoResponse lineaPedidoResponse = new LineaPedidoResponse();
                lineaPedidoResponse.setNombrePlato(lineaPedido.getPlato().getPlatoNombre());
                lineaPedidoResponse.setCantidadPlato(lineaPedido.getCantidad());
                lineaPedidoResponse.setPrecioUnitario(lineaPedido.getPlato().getPlatoPrecio());
                lineaPedidoResponse.setPrecioConjunto(lineaPedido.getPrecioTotal());
                return lineaPedidoResponse;
            })
            .collect(Collectors.toList());

        return lineasPedidoResponse;
    }
}