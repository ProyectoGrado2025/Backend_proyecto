package es.daw2.restaurant_V1.controllers.worker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.WorkerServiceGroup;
import es.daw2.restaurant_V1.dtos.platos.PlatoResponse;

@RestController
@RequestMapping("/worker")
public class PlatoWorkerController {

    @Autowired
    private WorkerServiceGroup servicios;

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
}
