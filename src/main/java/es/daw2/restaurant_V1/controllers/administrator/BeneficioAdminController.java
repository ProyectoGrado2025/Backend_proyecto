package es.daw2.restaurant_V1.controllers.administrator;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.config.AdminServiceGroup;
import es.daw2.restaurant_V1.dtos.beneficios.BeneficioRequest;
import es.daw2.restaurant_V1.dtos.beneficios.BeneficioResponse;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/admin")
public class BeneficioAdminController {

    @Autowired
    private AdminServiceGroup servicios;

    @GetMapping("/beneficios/listar")
    public ResponseEntity<Page<BeneficioResponse>> getAllBeneficios(Pageable pageable) {
        Page<BeneficioResponse> beneficiosPage = servicios.BENEFICIO.getAllBeneficios(pageable);
        if(beneficiosPage.hasContent()){
            return ResponseEntity.ok().body(beneficiosPage);
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/beneficios/listar/habilitados")
    public ResponseEntity<List<BeneficioResponse>> getBeneficiosHabilitados() {
        List<BeneficioResponse> beneficioResponses = servicios.BENEFICIO.getAllBeneficiosAvilables().stream()
                .map(beneficio -> servicios.BENEFICIO.composeBeneficioResponse(beneficio))
                .collect(Collectors.toList());
        if(beneficioResponses.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(beneficioResponses);
    }

    @GetMapping("/beneficios/info/{id}")
    public ResponseEntity<BeneficioResponse> getBeneficioById (@PathVariable Long id) {
        BeneficioResponse beneficioResponse = servicios.BENEFICIO.getBeneficioById(id);
        return ResponseEntity.ok().body(beneficioResponse);
    }
    
    @PostMapping("/beneficios/crear")
    public ResponseEntity<BeneficioResponse> crearBeneficio(@RequestBody BeneficioRequest beneficioRequest) {
        BeneficioResponse beneficioResponse = servicios.BENEFICIO.crearBeneficio(beneficioRequest);
        URI location = URI.create("/beneficios/info/"+beneficioResponse.getIdBeneficio())     ;
        return ResponseEntity.created(location).body(beneficioResponse);   
    }

    @PutMapping("/beneficios/{id}/deshabilitar")
    public ResponseEntity<BeneficioResponse> disableBeneficioById (@PathVariable Long id) {
        BeneficioResponse beneficioResponse = servicios.BENEFICIO.disableBeneficio(id);
        return ResponseEntity.ok().body(beneficioResponse);
    }

    @PutMapping("/beneficios/{id}/habilitar")
    public ResponseEntity<BeneficioResponse> enableBeneficioById (@PathVariable Long id) {
        BeneficioResponse beneficioResponse = servicios.BENEFICIO.enableBeneficio(id);
        return ResponseEntity.ok().body(beneficioResponse);
    }
}