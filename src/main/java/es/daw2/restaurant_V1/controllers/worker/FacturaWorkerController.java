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
import es.daw2.restaurant_V1.dtos.facturas.FacturaRequest;
import es.daw2.restaurant_V1.dtos.facturas.FacturaResponse;

@RestController
@RequestMapping("/worker")
public class FacturaWorkerController {

    @Autowired
    private WorkerServiceGroup servicios;

    @PostMapping("/facturas/crear")
    public ResponseEntity<FacturaResponse> crearFactura(@RequestBody FacturaRequest facturaRequest) {
        FacturaResponse facturaResponse = servicios.FACTURA.crearFactura(facturaRequest);
        URI location = URI.create("/facturas/detalle/" + facturaResponse.getFacturaId());
        return ResponseEntity.created(location).body(facturaResponse);
    }

    @GetMapping("/facturas/listar")
    public ResponseEntity<Page<FacturaResponse>> getAllFacturas(Pageable pageable) {
        Page<FacturaResponse> facturaPage = servicios.FACTURA.getAllFacturas(pageable);
        if(facturaPage.hasContent()){
            return ResponseEntity.ok().body(facturaPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/facturas/detalle/{id}")
    public ResponseEntity<FacturaResponse> getFacturaById(@PathVariable Long id) {
        return ResponseEntity.ok().body(servicios.FACTURA.getFacturaById(id));
    }
}
