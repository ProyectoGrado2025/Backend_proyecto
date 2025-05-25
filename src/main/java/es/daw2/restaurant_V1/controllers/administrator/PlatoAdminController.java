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
import es.daw2.restaurant_V1.dtos.platos.PlatoRequest;
import es.daw2.restaurant_V1.dtos.platos.PlatoResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class PlatoAdminController {

    @Autowired
    private AdminServiceGroup servicios;
    
    @PostMapping("/platos/crear")
    public ResponseEntity<PlatoResponse> crearPlato(@RequestBody @Valid PlatoRequest platoRequest) {
        PlatoResponse platoResponse = servicios.PLATO.crearPlato(platoRequest);
        URI location = URI.create("/platos/detalle/" + platoResponse.getPlatoId());
        return ResponseEntity.created(location).body(platoResponse);
    }

    @GetMapping("/platos/listar")
    public ResponseEntity<Page<PlatoResponse>> getAllPlatos(Pageable pageable) {
        Page<PlatoResponse> platosPage = servicios.PLATO.getAllPlatos(pageable);
        if(platosPage.hasContent()){
            return ResponseEntity.ok().body(platosPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/platos/detalle/{id}")
    public ResponseEntity<PlatoResponse> getPlatoById (@PathVariable Long id) {
        return ResponseEntity.ok().body(servicios.PLATO.getPlatoById(id));
    }

    @PutMapping("/platos/{id}/actualizar")
    public ResponseEntity<PlatoResponse> actualizarPlato(@PathVariable Long id, @RequestBody PlatoRequest platoRequest) {
        PlatoResponse platoResponse = servicios.PLATO.actualizarPlato(id, platoRequest);
        return ResponseEntity.ok().body(platoResponse);
    }
}