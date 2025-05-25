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
import es.daw2.restaurant_V1.dtos.categoria.CategoriaRequest;
import es.daw2.restaurant_V1.dtos.categoria.CategoriaResponse;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/admin")
public class CategoriaAdminController {

    @Autowired
    private AdminServiceGroup servicios;

    @PostMapping("/categorias/crear")
    public ResponseEntity<CategoriaResponse> crearAlergeno(@RequestBody @Valid CategoriaRequest categoriaRequest) {
        CategoriaResponse categoria = servicios.CATEGORIA.crearCategoria(categoriaRequest);
        URI location = URI.create("/categorias/detalle/" + categoria.getCategoriaId());
        return ResponseEntity.created(location).body(categoria);
    }

    @GetMapping("/categorias/listar")
    public ResponseEntity<Page<CategoriaResponse>> getAllCategorias(Pageable pageable) {
        Page<CategoriaResponse> categoriaPage = servicios.CATEGORIA.getAllCategorias(pageable);
        if(categoriaPage.hasContent()){
            return ResponseEntity.ok().body(categoriaPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/categorias/detalle/{id}")
    public ResponseEntity<CategoriaResponse> getCategoriaById (@PathVariable Long id) {
        return ResponseEntity.ok().body(servicios.CATEGORIA.getCategoriaById(id));
    }

    @PutMapping("/categorias/{id}/actualizar")
    public ResponseEntity<CategoriaResponse> updateCategoria(@PathVariable Long id, @RequestBody CategoriaRequest categoriaRequest) {
        CategoriaResponse categoriaResponse = servicios.CATEGORIA.actualizarCategoria(id, categoriaRequest);
        return ResponseEntity.ok().body(categoriaResponse);
    }
}
