package es.daw2.restaurant_V1.controllers.client;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.ClienteServiceGroup;
import es.daw2.restaurant_V1.dtos.reservas.ReservaClientRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaResponse;
import es.daw2.restaurant_V1.dtos.reservas.ReservaUpdateClientRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/client")
public class ReservaClientController {

    @Autowired
    private ClienteServiceGroup servicios;

    @GetMapping("/reservas/detalle/{id}")
    public ResponseEntity<ReservaResponse> getReservaByIdByClient (@PathVariable Long id, @RequestBody ReservaClientRequest reservaClientRequest) {
        return ResponseEntity.ok().body(servicios.RESERVA.findReservaByIdByClient(reservaClientRequest, id));
    }

    @PostMapping("/reservas/crear")
    public ResponseEntity<ReservaResponse> crearReserva(@RequestBody @Valid ReservaRequest reservaRequest) {
        ReservaResponse reservaResponse = servicios.RESERVA.crearReserva(reservaRequest);
        URI location = URI.create("/reservas/detalle"+reservaResponse.getReservaId());
        return ResponseEntity.created(location).body(reservaResponse);
    }

    @PutMapping("/reservas/{id}/actualizar")
    public ResponseEntity<ReservaResponse> actualizarReservaByClient(@PathVariable Long id, @RequestBody @Valid ReservaUpdateClientRequest reservaUpdateRequest) {
        ReservaResponse reservaResponse = servicios.RESERVA.actualizarReservaByClient(id, reservaUpdateRequest);
        return ResponseEntity.ok().body(reservaResponse);
    }

    @PutMapping("reservas/{id}/cancelar")
    public ResponseEntity<ReservaResponse> cancelarReservaByClient (@PathVariable Long id, @RequestBody ReservaClientRequest reservaCancelRequest) {
        ReservaResponse reservaResponse = servicios.RESERVA.cancelarReservaByClient(id, reservaCancelRequest);
        return ResponseEntity.ok().body(reservaResponse);
    }
}