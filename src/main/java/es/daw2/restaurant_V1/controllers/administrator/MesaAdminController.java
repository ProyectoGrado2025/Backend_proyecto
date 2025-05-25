package es.daw2.restaurant_V1.controllers.administrator;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.AdminServiceGroup;
import es.daw2.restaurant_V1.dtos.mesas.MesaRequest;
import es.daw2.restaurant_V1.dtos.mesas.MesaResponse;
import es.daw2.restaurant_V1.dtos.mesas.MesaUpdateRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class MesaAdminController {

    @Autowired
    private AdminServiceGroup servicios;

    @PostMapping("/mesas/crear")
    public ResponseEntity<MesaResponse> crearMesa(@RequestBody @Valid MesaRequest mesaRequest) {
        MesaResponse mesaResponse = servicios.MESA.crearMesa(mesaRequest);
        URI location = URI.create("/mesas/detalle/"+mesaResponse.getMesaId());
        return ResponseEntity.created(location).body(mesaResponse);
    }

    @GetMapping("/mesas/listar")
    public ResponseEntity<Page<MesaResponse>> getAllMesas(Pageable pageable) {
        Page<MesaResponse> mesaPage = servicios.MESA.getAllMesas(pageable);
        if(mesaPage.hasContent()){
            return ResponseEntity.ok().body(mesaPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/mesas/detalle/{id}")
    public ResponseEntity<MesaResponse> getMesaById (@PathVariable Long id) {
        return ResponseEntity.ok().body(servicios.MESA.getMesaById(id));
    }
    
    @PutMapping("/mesas/{id}/numero")
    public ResponseEntity<MesaResponse> cambiarNumeroMesa(@PathVariable Long id, @RequestBody MesaUpdateRequest mesaUpdateRequest) {
        return ResponseEntity.ok().body(servicios.MESA.intercambiarNumeroMesa(id, mesaUpdateRequest));
    }

    @PutMapping("/mesas/{id}/estado")
    public ResponseEntity<MesaResponse> cambiarStatusMesa(@PathVariable Long id, @RequestBody MesaUpdateRequest mesaUpdateRequest) {
        return ResponseEntity.ok().body(servicios.MESA.actualizarMesaStatus(id, mesaUpdateRequest));
    }
}
