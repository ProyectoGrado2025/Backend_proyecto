package es.daw2.restaurant_V1.controllers;

import java.net.URI;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.models.Cliente;
import es.daw2.restaurant_V1.models.Factura;
import es.daw2.restaurant_V1.models.Mesa;
import es.daw2.restaurant_V1.models.Pedido;
import es.daw2.restaurant_V1.models.Plato;
import es.daw2.restaurant_V1.models.PlatoCategoria;
import es.daw2.restaurant_V1.models.Reserva;
import es.daw2.restaurant_V1.services.IFServicioCliente;
import es.daw2.restaurant_V1.services.IFServicioFactura;
import es.daw2.restaurant_V1.services.IFServicioMesa;
import es.daw2.restaurant_V1.services.IFServicioPedido;
import es.daw2.restaurant_V1.services.IFServicioPlato;
import es.daw2.restaurant_V1.services.IFServicioPlatoCategoria;
import es.daw2.restaurant_V1.services.IFServicioReserva;

@RestController
@RequestMapping("/admin")
// Un usuario con rol "admin" tendrá acceso a todos los datos y las operaciones.
public class AdminController {
        
    @Autowired
    IFServicioReserva reservationsService;
    @Autowired
    IFServicioCliente clientsService;
    @Autowired
    IFServicioFactura invoicesService;
    @Autowired
    IFServicioPedido ordersService;
    @Autowired
    IFServicioPlato dishService;
    @Autowired
    IFServicioPlatoCategoria categoryService;
    @Autowired
    IFServicioMesa tableService;

    // SECCIÓN DE OPERACIONES DE RESERVAS
    @GetMapping("/getclientreservation/{id}")
    public ResponseEntity<?> getClientReservation(@PathVariable Long id) {
        Optional<Reserva> reservaContainer = reservationsService.getReservationById(id);

        if (reservaContainer.isPresent()) {
            return ResponseEntity.ok().body(reservaContainer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getreservas")
    public ResponseEntity<?> getAllReservations (){
        return ResponseEntity.ok().body(reservationsService.getReservations());
    }

    @PostMapping("/createclientreservation")
    public ResponseEntity<?> postClientReservation(@RequestBody Reserva clientReservation) {
        boolean created = reservationsService.addReservation(clientReservation);

        if (created) {
            // Suponiendo que la reserva ahora tiene un ID asignado tras ser guardada... ESPEREMOS QUE FUNCIONE
            URI location = URI.create("/client/getclientreservation/" + clientReservation.getReserva_id());
            return ResponseEntity.created(location).body(clientReservation);
        } else {
            return ResponseEntity.badRequest().body("ERROR AL CREAR LA RESERVA");
        }
    }

    @PutMapping("/updateclientreservation/{id}")
    public ResponseEntity<?> putClientReservation(@PathVariable Long id, @RequestBody Reserva clientReservation) {
        boolean updated = reservationsService.updateReservation(clientReservation, id);

        if(updated){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ERROR AL ACTUALIZAR LA RESERVA");
        }
    }

    @DeleteMapping("/deleteclientreservation/{id}")
    public ResponseEntity<?> deleteClientReservation (@PathVariable Long id){
        boolean deleted = reservationsService.deleteReservation(id);

        if(deleted){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // SECCIÓN DE OPERACIONES DE CLIENTES
    @GetMapping("/getclient/{id}")
    public ResponseEntity<?> getClient(@PathVariable Long id) {
        Optional<Cliente> clientContainer = clientsService.getClientById(id);

        if (clientContainer.isPresent()) {
            return ResponseEntity.ok().body(clientContainer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getclients")
    public ResponseEntity<?> getAllClients (){
        return ResponseEntity.ok().body(clientsService.getClients());
    }

    @PostMapping("/createclient")
    public ResponseEntity<?> postClient(@RequestBody Cliente client) {
        boolean created = clientsService.createClient(client);

        if(created){
            URI location = URI.create("/admin/getclient/" + client.getId());
            return ResponseEntity.created(location).body(client);
        } else {
            return ResponseEntity.badRequest().body("ERROR AL CREAR UN CLIENTE"); 
        }

    }

    @PutMapping("/updateclient/{id}")
    public ResponseEntity<?> putClient(@PathVariable Long id, @RequestBody Cliente client) {
        boolean updated = clientsService.updateClient(client, id);

        if(updated){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ERROR AL ACTUALIZAR AL CLIENTE");
        }
    }

    @DeleteMapping("/deleteclient/{id}")
    public ResponseEntity<?> deleteClient (@PathVariable Long id){
        boolean deleted = clientsService.deleteCliente(id);

        if(deleted){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // SECCIÓN DE OPERACIONES DE FACTURAS
    @GetMapping("/getinvoice/{id}")
    public ResponseEntity<?> getInvoice(@PathVariable Long id) {
        Optional<Factura> invoiceContainer = invoicesService.getInvoiceById(id);

        if (invoiceContainer.isPresent()) {
            return ResponseEntity.ok().body(invoiceContainer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getinvoices")
    public ResponseEntity<?> getAllInvoices (){
        return ResponseEntity.ok().body(invoicesService.getInvoices());
    }

    @PostMapping("/createinvoice")
    public ResponseEntity<?> postInvoice(@RequestBody Factura invoice) {
        boolean created = invoicesService.createInvoice(invoice);

        if(created){
            URI location = URI.create("/admin/getinvoice/" + invoice.getFactura_id());
            return ResponseEntity.created(location).body(invoice);
        } else {
            return ResponseEntity.badRequest().body("ERROR AL CREAR UN CLIENTE"); 
        }

    }

    @PutMapping("/updateinvoice/{id}")
    public ResponseEntity<?> putInvoice(@PathVariable Long id, @RequestBody Factura invoice) {
        boolean updated = invoicesService.updateInvoice(invoice, id);

        if(updated){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ERROR AL ACTUALIZAR AL CLIENTE");
        }
    }

    @DeleteMapping("/deleteinvoice/{id}")
    public ResponseEntity<?> deleteInvoice(@PathVariable Long id){
        boolean deleted = invoicesService.deleteInvoice(id);

        if(deleted){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // SECCIÓN DE OPERACIONES DE PEDIDOS
    @GetMapping("/getorder/{id}")
    public ResponseEntity<?> getOrder(@PathVariable Long id) {
        Optional<Pedido> orderContainer = ordersService.getOrderById(id);

        if (orderContainer.isPresent()) {
            return ResponseEntity.ok().body(orderContainer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getorders")
    public ResponseEntity<?> getAllOrders (){
        return ResponseEntity.ok().body(ordersService.getOrders());
    }

    @PostMapping("/createorder")
    public ResponseEntity<?> postOrder(@RequestBody Pedido order) {
        boolean created = ordersService.createOrder(order);

        if(created){
            URI location = URI.create("/admin/getinvoice/" + order.getPedido_id());
            return ResponseEntity.created(location).body(order);
        } else {
            return ResponseEntity.badRequest().body("ERROR AL CREAR EL PEDIDO"); 
        }

    }

    @PutMapping("/updateorder/{id}")
    public ResponseEntity<?> putOrder(@PathVariable Long id, @RequestBody Pedido order) {
        boolean updated = ordersService.updateOrder(order, id);

        if(updated){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().body("ERROR AL ACTUALIZAR AL CLIENTE");
        }
    }

    @DeleteMapping("/deleteorder/{id}")
    public ResponseEntity<?> deleteOrder(@PathVariable Long id){
        boolean deleted = ordersService.deleteOrder(id);

        if(deleted){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // SECCIÓN DE OPERACIONES DE PLATOS
    @GetMapping("/getdish/{id}")
    public ResponseEntity<?> getDish(@PathVariable Long id) {
        Optional<Plato> dishContainer = dishService.getDishById(id);

        if (dishContainer.isPresent()) {
            return ResponseEntity.ok().body(dishContainer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getdishes")
    public ResponseEntity<?> getAllDishes (){
        return ResponseEntity.ok().body(dishService.getDishes());
    }

    @PostMapping("/createdish")
    public ResponseEntity<?> postDish(@RequestBody Plato dish) {
        boolean created = dishService.addDish(dish);

        if(created){
            URI location = URI.create("/admin/getdish/" + dish.getPlato_id());
            return ResponseEntity.created(location).body(dish);
        } else {
            return ResponseEntity.badRequest().body("ERROR AL CREAR EL PLATO"); 
        }

    }
    // SECCIÓN DE OPERACIONES DE CATEGORÍAS
    @GetMapping("/getcategory/{id}")
    public ResponseEntity<?> getCategory(@PathVariable Long id) {
        Optional<PlatoCategoria> categoryContainer = categoryService.getCategoryById(id);

        if (categoryContainer.isPresent()) {
            return ResponseEntity.ok().body(categoryContainer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/getcategories")
    public ResponseEntity<?> getAllCategories (){
        return ResponseEntity.ok().body(categoryService.getCategories());
    }

    @PostMapping("/createcategory")
    public ResponseEntity<?> postCategory(@RequestBody PlatoCategoria category) {
        boolean created = categoryService.addCategory(category);

        if(created){
            URI location = URI.create("/admin/getcategory/" + category.getCategoria_id());
            return ResponseEntity.created(location).body(category);
        } else {
            return ResponseEntity.badRequest().body("ERROR AL CREAR UNA CATEGORÍA"); 
        }

    }
    // SECCIÓN DE OPERACIONES DE MESAS
    @GetMapping("/gettable/{id}")
    public ResponseEntity<?> getTable(@PathVariable Long id) {
        Optional<Mesa> tableContainer = tableService.getMesaById(id);

        if (tableContainer.isPresent()) {
            return ResponseEntity.ok().body(tableContainer.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/gettables")
    public ResponseEntity<?> getAllTables (){
        return ResponseEntity.ok().body(tableService.getMesas());
    }

    @PostMapping("/createtable")
    public ResponseEntity<?> postTables(@RequestBody Mesa table) {
        boolean created = tableService.addMesa(table);

        if(created){
            URI location = URI.create("/admin/gettable/" + table.getMesa_id());
            return ResponseEntity.created(location).body(table);
        } else {
            return ResponseEntity.badRequest().body("ERROR AL CREAR UNA CATEGORÍA"); 
        }

    }
}
