package es.daw2.restaurant_V1.controllers.client;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.dtos.clientes.ClienteRangoResponse;
import es.daw2.restaurant_V1.services.interfaces.IFServicioCliente;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/client")
public class RangoClientController {

    @Autowired
    IFServicioCliente servicioCliente;

    @GetMapping("/consultar/{id}/puntos")
    public ResponseEntity<ClienteRangoResponse> consultarPuntosPorId (@RequestParam("email") String clienteEmail, @PathVariable Long id) {
        ClienteRangoResponse clienteRangoResponse = servicioCliente.consultarRangoInfoById(clienteEmail, id);
        return ResponseEntity.ok().body(clienteRangoResponse);
    }
}
