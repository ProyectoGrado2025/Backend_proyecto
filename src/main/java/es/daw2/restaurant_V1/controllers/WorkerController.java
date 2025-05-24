package es.daw2.restaurant_V1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.services.interfaces.IFServicioCliente;
import es.daw2.restaurant_V1.services.interfaces.IFServicioFactura;
import es.daw2.restaurant_V1.services.interfaces.IFServicioPedido;
import es.daw2.restaurant_V1.services.interfaces.IFServicioReserva;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@CrossOrigin
@RestController
@RequestMapping("/worker")
// Un usuario con rol "worker" solo tendrá acceso a la lectura de los datos de las reservas, cliente, facturas y pedidos
public class WorkerController {
    
    @Autowired
    IFServicioReserva reservationsService;
    @Autowired
    IFServicioCliente clientsService;
    @Autowired
    IFServicioFactura invoicesService;
    @Autowired
    IFServicioPedido ordersService;

    @GetMapping("/getreservas")
    public ResponseEntity<?> getAllReservations (){
        return ResponseEntity.ok().body(reservationsService.getReservations());
    }

    @GetMapping("/getclientes")
    public ResponseEntity<?> getAllClients() {
        return ResponseEntity.ok().body(clientsService.getClients());
    }

    @GetMapping("/getfacturas")
    public ResponseEntity<?> getAllInvoices() {
        return ResponseEntity.ok().body(invoicesService.getInvoices());
    }
    
    @GetMapping("/getpedidos")
    public ResponseEntity<?> getAllOrders() {
        return ResponseEntity.ok().body(ordersService.getOrders());
    }
    
}
