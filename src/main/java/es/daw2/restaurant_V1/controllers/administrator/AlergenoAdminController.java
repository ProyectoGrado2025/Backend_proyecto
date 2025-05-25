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
import es.daw2.restaurant_V1.dtos.alergenos.AlergenoRequest;
import es.daw2.restaurant_V1.models.Alergeno;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class AlergenoAdminController {

    @Autowired
    private AdminServiceGroup servicios;

    @PostMapping("/alergenos/crear")
    public ResponseEntity<Alergeno> crearAlergeno(@RequestBody @Valid AlergenoRequest alergenoRequest) {
        Alergeno alergeno = servicios.ALERGENO.crearAlergeno(alergenoRequest);
        URI location = URI.create("/alergenos/detalle/" + alergeno.getAlergenoId());
        return ResponseEntity.created(location).body(alergeno);
    }

    @GetMapping("/alergenos/listar")
    public ResponseEntity<Page<Alergeno>> getAllAlergenos(Pageable pageable) {
        Page<Alergeno> alergenoPage = servicios.ALERGENO.getAllAlergenos(pageable);
        if(alergenoPage.hasContent()){
            return ResponseEntity.ok().body(alergenoPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/alergenos/detalle/{id}")
    public ResponseEntity<Alergeno> getAlergenoById (@PathVariable Long id) {
        return ResponseEntity.ok().body(servicios.ALERGENO.getAlergenoById(id));
    }

    @PutMapping("/alergenos/{id}/actualizar")
    public ResponseEntity<Alergeno> updateAlergeno(@PathVariable Long id, @RequestBody AlergenoRequest alergenoRequest) {
        Alergeno alergeno = servicios.ALERGENO.updateAlergeno(id, alergenoRequest);
        return ResponseEntity.ok().body(alergeno);
    }
}
