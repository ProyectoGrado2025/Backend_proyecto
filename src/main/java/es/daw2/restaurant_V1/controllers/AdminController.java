package es.daw2.restaurant_V1.controllers;

import java.net.URI;
// import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.AdminServiceGroup;
import es.daw2.restaurant_V1.dtos.alergenos.AlergenoRequest;
import es.daw2.restaurant_V1.dtos.mesas.MesaRequest;
import es.daw2.restaurant_V1.dtos.mesas.MesaResponse;
import es.daw2.restaurant_V1.models.Alergeno;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
// Un usuario con rol "admin" tendrá acceso a todos los datos y las operaciones.
public class AdminController {
    
    @Autowired
    private AdminServiceGroup servicios;

    // // SECCIÓN DE OPERACIONES DE RESERVAS
    // @GetMapping("/getclientreservation/{id}")
    // public ResponseEntity<?> getClientReservation(@PathVariable Long id) {
    //     Optional<Reserva> reservaContainer = reservaServicio.getReservationById(id);

    //     if (reservaContainer.isPresent()) {
    //         return ResponseEntity.ok().body(reservaContainer.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @GetMapping("/getreservas")
    // public ResponseEntity<?> getAllReservations (){
    //     return ResponseEntity.ok().body(reservaServicio.getReservations());
    // }

    // @PostMapping("/createclientreservation")
    // public ResponseEntity<?> postClientReservation(@RequestBody Reserva clientReservation) {
    //     boolean created = reservaServicio.addReservation(clientReservation);

    //     if (created) {
    //         // Suponiendo que la reserva ahora tiene un ID asignado tras ser guardada... ESPEREMOS QUE FUNCIONE
    //         URI location = URI.create("/client/getclientreservation/" + clientReservation.getReservaId());
    //         return ResponseEntity.created(location).body(clientReservation);
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL CREAR LA RESERVA");
    //     }
    // }

    // @PutMapping("/updateclientreservation/{id}")
    // public ResponseEntity<?> putClientReservation(@PathVariable Long id, @RequestBody Reserva clientReservation) {
    //     boolean updated = reservaServicio.updateReservation(clientReservation, id);

    //     if(updated){
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL ACTUALIZAR LA RESERVA");
    //     }
    // }

    // @DeleteMapping("/deleteclientreservation/{id}")
    // public ResponseEntity<?> deleteClientReservation (@PathVariable Long id){
    //     boolean deleted = reservaServicio.deleteReservation(id);

    //     if(deleted){
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // // SECCIÓN DE OPERACIONES DE CLIENTES
    // @GetMapping("/getclient/{id}")
    // public ResponseEntity<?> getClient(@PathVariable Long id) {
    //     Optional<Cliente> clientContainer = clienteServicio.getClientById(id);

    //     if (clientContainer.isPresent()) {
    //         return ResponseEntity.ok().body(clientContainer.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @GetMapping("/getclients")
    // public ResponseEntity<?> getAllClients (){
    //     return ResponseEntity.ok().body(clienteServicio.getClients());
    // }

    // @PostMapping("/createclient")
    // public ResponseEntity<?> postClient(@RequestBody Cliente client) {
    //     boolean created = clienteServicio.createClient(client);

    //     if(created){
    //         URI location = URI.create("/admin/getclient/" + client.getId());
    //         return ResponseEntity.created(location).body(client);
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL CREAR UN CLIENTE"); 
    //     }
    // }

    // @PutMapping("/updateclient/{id}")
    // public ResponseEntity<?> putClient(@PathVariable Long id, @RequestBody Cliente client) {
    //     boolean updated = clienteServicio.updateClient(client, id);

    //     if(updated){
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL ACTUALIZAR AL CLIENTE");
    //     }
    // }

    // @DeleteMapping("/deleteclient/{id}")
    // public ResponseEntity<?> deleteClient (@PathVariable Long id){
    //     boolean deleted = clienteServicio.deleteCliente(id);

    //     if(deleted){
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // // SECCIÓN DE OPERACIONES DE FACTURAS
    // @GetMapping("/getinvoice/{id}")
    // public ResponseEntity<?> getInvoice(@PathVariable Long id) {
    //     Optional<Factura> invoiceContainer = facturaServicio.getInvoiceById(id);

    //     if (invoiceContainer.isPresent()) {
    //         return ResponseEntity.ok().body(invoiceContainer.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @GetMapping("/getinvoices")
    // public ResponseEntity<?> getAllInvoices (){
    //     return ResponseEntity.ok().body(facturaServicio.getInvoices());
    // }

    // @PostMapping("/createinvoice")
    // public ResponseEntity<?> postInvoice(@RequestBody Factura invoice) {
    //     boolean created = facturaServicio.createInvoice(invoice);

    //     if(created){
    //         URI location = URI.create("/admin/getinvoice/" + invoice.getFacturaId());
    //         return ResponseEntity.created(location).body(invoice);
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL CREAR LA FACTURA"); 
    //     }
    // }

    // @PutMapping("/updateinvoice/{id}")
    // public ResponseEntity<?> putInvoice(@PathVariable Long id, @RequestBody Factura invoice) {
    //     boolean updated = facturaServicio.updateInvoice(invoice, id);

    //     if(updated){
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL ACTUALIZAR LA FACTURA");
    //     }
    // }

    // @DeleteMapping("/deleteinvoice/{id}")
    // public ResponseEntity<?> deleteInvoice(@PathVariable Long id){
    //     boolean deleted = facturaServicio.deleteInvoice(id);

    //     if(deleted){
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // // SECCIÓN DE OPERACIONES DE PEDIDOS
    // @GetMapping("/getorder/{id}")
    // public ResponseEntity<?> getOrder(@PathVariable Long id) {
    //     Optional<Pedido> orderContainer = pedidoServicio.getOrderById(id);

    //     if (orderContainer.isPresent()) {
    //         return ResponseEntity.ok().body(orderContainer.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @GetMapping("/getorders")
    // public ResponseEntity<?> getAllOrders (){
    //     return ResponseEntity.ok().body(pedidoServicio.getOrders());
    // }

    // @PostMapping("/createorder")
    // public ResponseEntity<?> postOrder(@RequestBody Pedido order) {
    //     boolean created = pedidoServicio.createOrder(order);

    //     if(created){
    //         URI location = URI.create("/admin/getinvoice/" + order.getPedido_id());
    //         return ResponseEntity.created(location).body(order);
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL CREAR EL PEDIDO"); 
    //     }

    // }

    // @PutMapping("/updateorder/{id}")
    // public ResponseEntity<?> putOrder(@PathVariable Long id, @RequestBody Pedido order) {
    //     boolean updated = pedidoServicio.updateOrder(order, id);

    //     if(updated){
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL ACTUALIZAR AL CLIENTE");
    //     }
    // }

    // @DeleteMapping("/deleteorder/{id}")
    // public ResponseEntity<?> deleteOrder(@PathVariable Long id){
    //     boolean deleted = pedidoServicio.deleteOrder(id);

    //     if(deleted){
    //         return ResponseEntity.ok().build();
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // // SECCIÓN DE OPERACIONES DE PLATOS
    // @GetMapping("/getdish/{id}")
    // public ResponseEntity<?> getDish(@PathVariable Long id) {
    //     Optional<Plato> dishContainer = platoServicio.getDishById(id);

    //     if (dishContainer.isPresent()) {
    //         return ResponseEntity.ok().body(dishContainer.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @GetMapping("/getdishes")
    // public ResponseEntity<?> getAllDishes (){
    //     return ResponseEntity.ok().body(platoServicio.getDishes());
    // }

    // @PostMapping("/createdish")
    // public ResponseEntity<?> postDish(@RequestBody Plato dish) {
    //     boolean created = platoServicio.addDish(dish);

    //     if(created){
    //         URI location = URI.create("/admin/getdish/" + dish.getPlatoId());
    //         return ResponseEntity.created(location).body(dish);
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL CREAR EL PLATO"); 
    //     }

    // }
    // // SECCIÓN DE OPERACIONES DE CATEGORÍAS
    // @GetMapping("/getcategory/{id}")
    // public ResponseEntity<?> getCategory(@PathVariable Long id) {
    //     Optional<PlatoCategoria> categoryContainer = categoriaServicio.getCategoryById(id);

    //     if (categoryContainer.isPresent()) {
    //         return ResponseEntity.ok().body(categoryContainer.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @GetMapping("/getcategories")
    // public ResponseEntity<?> getAllCategories (){
    //     return ResponseEntity.ok().body(categoriaServicio.getCategories());
    // }

    // @PostMapping("/createcategory")
    // public ResponseEntity<?> postCategory(@RequestBody PlatoCategoria category) {
    //     boolean created = categoriaServicio.addCategory(category);

    //     if(created){
    //         URI location = URI.create("/admin/getcategory/" + category.getCategoriaId());
    //         return ResponseEntity.created(location).body(category);
    //     } else {
    //         return ResponseEntity.badRequest().body("ERROR AL CREAR UNA CATEGORÍA"); 
    //     }

    // }
    // // SECCIÓN DE OPERACIONES DE MESAS
    // @GetMapping("/gettable/{id}")
    // public ResponseEntity<?> getTable(@PathVariable Long id) {
    //     Optional<Mesa> tableContainer = mesaServicio.getMesaById(id);

    //     if (tableContainer.isPresent()) {
    //         return ResponseEntity.ok().body(tableContainer.get());
    //     } else {
    //         return ResponseEntity.notFound().build();
    //     }
    // }

    // @GetMapping("/gettables")
    // public ResponseEntity<?> getAllTables (){
    //     return ResponseEntity.ok().body(mesaServicio.getMesas());
    // }

    /*
     * MODIFICADO 19/05/25
     */
    @PostMapping("/crear-mesa")
    public ResponseEntity<MesaResponse> crearMesa(@RequestBody @Valid MesaRequest mesaRequest) {
        MesaResponse mesaResponse = servicios.MESA.addMesa(mesaRequest);
        URI location = URI.create("/gettable/"+mesaResponse.getMesaId());
        return ResponseEntity.created(location).body(mesaResponse);
    }

    /***************************************************************************
     *                            SECCIÓN DE ALÉRGENOS                         *
     ***************************************************************************/
    @PostMapping("/crear-alergeno")
    public ResponseEntity<Alergeno> crearAlergeno(@RequestBody @Valid AlergenoRequest alergenoRequest) {
        Alergeno alergeno = servicios.ALERGENO.crearAlergeno(alergenoRequest);
        URI location = URI.create("/consultar-alergeno/" + alergeno.getAlergenoId());
        return ResponseEntity.created(location).body(alergeno);
    }

    @GetMapping("/consultar-alergenos")
    public ResponseEntity<Page<Alergeno>> getAllAlergenos(Pageable pageable) {
        Page<Alergeno> alergenoPage = servicios.ALERGENO.getAllAlergenos(pageable);
        if(alergenoPage.hasContent()){
            return ResponseEntity.ok().body(alergenoPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/consultar-alergeno/{id}")
    public ResponseEntity<Alergeno> getAlergenoById (@PathVariable Long id) {
        return ResponseEntity.ok().body(servicios.ALERGENO.getAlergenoById(id));
    }

    @PutMapping("update-alergeno/{id}")
    public ResponseEntity<Alergeno> updateAlergeno(@PathVariable Long id, @RequestBody AlergenoRequest alergenoRequest) {
        Alergeno alergeno = servicios.ALERGENO.updateAlergeno(id, alergenoRequest);
        return ResponseEntity.ok().body(alergeno);
    }
    // ================================[ FIN ]================================ //
}   
