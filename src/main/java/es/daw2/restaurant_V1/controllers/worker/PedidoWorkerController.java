package es.daw2.restaurant_V1.controllers.worker;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.WorkerServiceGroup;
import es.daw2.restaurant_V1.dtos.pedidos.PedidoRequest;
import es.daw2.restaurant_V1.dtos.pedidos.PedidoResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/worker")
public class PedidoWorkerController {

    @Autowired
    private WorkerServiceGroup servicios;

    @PostMapping("/pedidos/crear")
    public ResponseEntity<PedidoResponse> crearPedido(@RequestBody @Valid PedidoRequest pedidoRequest) {
        PedidoResponse pedidoResponse = servicios.PEDIDO.crearPedido(pedidoRequest);
        URI location = URI.create("/pedidos/detalle/" + pedidoResponse.getPedidoId());
        return ResponseEntity.created(location).body(pedidoResponse);
    }

    @GetMapping("/pedidos/listar")
    public ResponseEntity<Page<PedidoResponse>> getAllPedidos(Pageable pageable) {
        Page<PedidoResponse> pedidosPage = servicios.PEDIDO.getAllPedidos(pageable);
        if(pedidosPage.hasContent()){
            return ResponseEntity.ok().body(pedidosPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/pedidos/detalle/{id}")
    public ResponseEntity<PedidoResponse> getPedidoById (@PathVariable Long id) {
        return ResponseEntity.ok().body(servicios.PEDIDO.getPedidoById(id));
    }

}
