package es.daw2.restaurant_V1.controllers.client;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.ClienteServiceGroup;
import es.daw2.restaurant_V1.models.Alergeno;

@RestController
@RequestMapping("/client")
public class AlergenoClientController {

    @Autowired
    private ClienteServiceGroup servicios;

    @GetMapping("/alergenos/listar")
    public ResponseEntity<Page<Alergeno>> getAllAlergenos(Pageable pageable) {
        Page<Alergeno> alergenoPage = servicios.ALERGENO.getAllAlergenos(pageable);
        if(alergenoPage.hasContent()){
            return ResponseEntity.ok().body(alergenoPage);
        }
        return ResponseEntity.notFound().build();
    }
}