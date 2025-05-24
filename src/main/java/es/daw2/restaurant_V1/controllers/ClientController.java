package es.daw2.restaurant_V1.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.dtos.reservas.ReservaRequest;
import es.daw2.restaurant_V1.dtos.reservas.ReservaResponse;
import es.daw2.restaurant_V1.dtos.reservas.ReservaUpdateRequest;
import es.daw2.restaurant_V1.models.Cliente;
import es.daw2.restaurant_V1.models.Reserva;
import es.daw2.restaurant_V1.services.interfaces.IFServicioCliente;
import es.daw2.restaurant_V1.services.interfaces.IFServicioReserva;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PutMapping;

@CrossOrigin
@RestController
@RequestMapping("/client")
// Un usuario con rol "client" podrá leer, crear, actualizar y borrar datos relativos a su reserva. Pero nunca tendrá acceso a las demás reservas.
public class ClientController {

    @Autowired
    IFServicioReserva reservaServicio;
    @Autowired
    IFServicioCliente clienteServicio;

    @GetMapping("/getclientreservation/{id}")
    public ResponseEntity<?> getClientReservation(@PathVariable Long id) {
        Optional<Reserva> reservaContainer = reservaServicio.getReservationById(id);

        if (reservaContainer.isPresent()) {
            return ResponseEntity.ok().body(reservaContainer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getclient/{id}")
    public ResponseEntity<?> getClient(@PathVariable Long id) {
        Optional<Cliente> clientContainer = clienteServicio.getClientById(id);

        if(clientContainer.isPresent()){
            return ResponseEntity.ok().body(clientContainer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    

    @PostMapping("/createclient")
    public ResponseEntity<?> postClient(@RequestBody Cliente client) {
        boolean created = clienteServicio.createClient(client);

        if(created){
            URI location = URI.create("/client/getclient/" + client.getId());
            return ResponseEntity.created(location).body(client);
        } else {
            return ResponseEntity.badRequest().body("ERROR AL CREAR UN CLIENTE"); 
        }

    }
    

    @PostMapping("/createclientreservation")
    public ResponseEntity<?> postClientReservation(@RequestBody Reserva clientReservation) {
        boolean created = reservaServicio.addReservation(clientReservation);

        if (created) {
            // Suponiendo que la reserva ahora tiene un ID asignado tras ser guardada... ESPEREMOS QUE FUNCIONE
            URI location = URI.create("/client/getclientreservation/" + clientReservation.getReservaId());
            return ResponseEntity.created(location).body(clientReservation);
        } else {
            return ResponseEntity.badRequest().body("ERROR AL CREAR LA RESERVA");
        }
    }

    @PutMapping("/updateclientreservation/{id}")
    public ResponseEntity<?> putClientReservation(@PathVariable Long id, @RequestBody Reserva clientReservation) {
        boolean updated = reservaServicio.updateReservation(clientReservation, id);

        if(updated){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ERROR AL ACTUALIZAR LA RESERVA");
        }
    }

    @DeleteMapping("/deleteclientreservation/{id}")
    public ResponseEntity<?> deleteClientReservation (@PathVariable Long id){
        boolean deleted = reservaServicio.deleteReservation(id);

        if(deleted){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    /**
     * NUEVOS ENDPOINTS 20/05/2025
     */
    @PostMapping("crear-reserva")
    public ResponseEntity<ReservaResponse> crearReserva(@RequestBody @Valid ReservaRequest reservaRequest) {
        ReservaResponse reservaResponse = reservaServicio.crearReserva(reservaRequest);
        URI location = URI.create("/client/getclientreservation/"+reservaResponse.getReservaId());
        return ResponseEntity.created(location).body(reservaResponse);
    }

    @PutMapping("actualizar-reserva/{id}")
    public ResponseEntity<ReservaResponse> actualizarReserva(@PathVariable Long id, @RequestBody @Valid ReservaUpdateRequest reservaUpdateRequest) {
        ReservaResponse reservaResponse = reservaServicio.actualizarReserva(id, reservaUpdateRequest);
        return ResponseEntity.ok().body(reservaResponse);
    }
}
