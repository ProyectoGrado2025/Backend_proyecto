package es.daw2.restaurant_V1.controllers.administrator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.AdminServiceGroup;
import es.daw2.restaurant_V1.dtos.clientes.ClienteResponse;

@RestController
@RequestMapping("/admin")
public class ClienteAdminController {

    @Autowired
    private AdminServiceGroup servicios;

    @GetMapping("/clientes/listar")
    public ResponseEntity<Page<ClienteResponse>> getAllClientes (Pageable pageable){
        Page<ClienteResponse> clientes = servicios.CLIENTE.getAllClientes(pageable);
        if(clientes.hasContent()){
            return ResponseEntity.ok().body(clientes);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/clientes/consultar/{id}")
    public ResponseEntity<ClienteResponse> getClienteById(@PathVariable Long id){
        return ResponseEntity.ok().body(servicios.CLIENTE.getClienteById(id));
    }
}