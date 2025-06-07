package es.daw2.restaurant_V1.controllers.administrator;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.AdminServiceGroup;
import es.daw2.restaurant_V1.dtos.rangos.RangoRequest;
import es.daw2.restaurant_V1.dtos.rangos.RangoResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/admin")
public class RangoAdminController {

    @Autowired
    private AdminServiceGroup servicios;

    @GetMapping("/rangos/listar")
    public ResponseEntity<Page<RangoResponse>> getAllRangos(Pageable pageable) {
        Page<RangoResponse> rangoPage = servicios.RANGO.getAllRangos(pageable);
        if(rangoPage.hasContent()){
            return ResponseEntity.ok().body(rangoPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/rangos/info/{id}")
    public ResponseEntity<RangoResponse> getRangoById(@PathVariable Long id) {
        RangoResponse rangoResponse = servicios.RANGO.getRangoById(id);
        return ResponseEntity.ok().body(rangoResponse);
    }

    @PostMapping("/rangos/crear")
    public ResponseEntity<RangoResponse> crearRango (@RequestBody RangoRequest rangoRequest) {
        RangoResponse rangoResponse = servicios.RANGO.crearRango(rangoRequest);
        URI location = URI.create("/rangos/info/"+rangoResponse.getRangoId());
        return ResponseEntity.created(location).body(rangoResponse);
    }
}