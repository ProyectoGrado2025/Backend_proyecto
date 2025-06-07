package es.daw2.restaurant_V1.controllers.stats;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.dtos.stadistics.platos.PlatoVentasDTO;
import es.daw2.restaurant_V1.services.stadistics.interfaces.IFPlatoStadisticsService;

@RestController
@RequestMapping("/stats")
public class PublicStatsController {

    @Autowired
    IFPlatoStadisticsService platoStadisticsService;

    @GetMapping("/platos/top")
    public ResponseEntity<List<PlatoVentasDTO>> getTop3Platos (){
        List<PlatoVentasDTO> platos = platoStadisticsService.findTop3PlatosMasVendidos();
        if(platos.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok().body(platos);
    }
}