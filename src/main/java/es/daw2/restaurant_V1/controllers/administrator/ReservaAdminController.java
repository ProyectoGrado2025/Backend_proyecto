package es.daw2.restaurant_V1.controllers.administrator;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.AdminServiceGroup;
import es.daw2.restaurant_V1.dtos.reservas.ReservaRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaResponse;
import es.daw2.restaurant_V1.dtos.reservas.ReservaUpdateRequest;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/admin")
public class ReservaAdminController {

    @Autowired
    private AdminServiceGroup servicios;

    @GetMapping("/reservas/listar")
    public ResponseEntity<Page<ReservaResponse>> getAllReservas(Pageable pageable) {
        Page<ReservaResponse> reservaPage = servicios.RESERVA.getAllReservas(pageable);
        if(reservaPage.hasContent()){
            return ResponseEntity.ok().body(reservaPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/reservas/listar/hoy")
    public ResponseEntity<Page<ReservaResponse>> getTodayReservas(Pageable pageable) {
        Page<ReservaResponse> reservaPage = servicios.RESERVA.getTodayReservas(pageable);
        if(reservaPage.hasContent()){
            return ResponseEntity.ok().body(reservaPage);
        }
        return ResponseEntity.notFound().build();
    }
    
    @GetMapping("/reservas/detalle/{id}")
    public ResponseEntity<ReservaResponse> getMesaById (@PathVariable Long id) {
        return ResponseEntity.ok().body(servicios.RESERVA.findReservaById(id));
    }
    

    @PostMapping("/reservas/crear")
    public ResponseEntity<ReservaResponse> crearReserva(@RequestBody @Valid ReservaRequest reservaRequest) {
        ReservaResponse reservaResponse = servicios.RESERVA.crearReserva(reservaRequest);
        URI location = URI.create("/reservas/detalle"+reservaResponse.getReservaId());
        return ResponseEntity.created(location).body(reservaResponse);
    }

    @PutMapping("/reservas/{id}/actualizar")
    public ResponseEntity<ReservaResponse> actualizarReserva(@PathVariable Long id, @RequestBody @Valid ReservaUpdateRequest reservaUpdateRequest) {
        ReservaResponse reservaResponse = servicios.RESERVA.actualizarReserva(id, reservaUpdateRequest);
        return ResponseEntity.ok().body(reservaResponse);
    }

    @PutMapping("/reservas/{id}/cancelar")
    public ResponseEntity<ReservaResponse> cancelarReservaAdmin(@PathVariable Long id) {
        ReservaResponse reservaResponse = servicios.RESERVA.cancelarReservaByAdmin(id);
        return ResponseEntity.ok().body(reservaResponse);
    }
}