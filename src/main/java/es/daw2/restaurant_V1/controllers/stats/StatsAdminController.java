package es.daw2.restaurant_V1.controllers.stats;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import es.daw2.restaurant_V1.services.email.EmailClient;
import es.daw2.restaurant_V1.services.stadistics.interfaces.IFClienteStadisticsService;
import es.daw2.restaurant_V1.services.stadistics.interfaces.IFFacturaStadisticsService;
import es.daw2.restaurant_V1.services.stadistics.interfaces.IFPlatoStadisticsService;
import es.daw2.restaurant_V1.services.stadistics.interfaces.IFReservaStadisticsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@RestController
@RequestMapping("/admin")
public class StatsAdminController {

    @Autowired
    IFReservaStadisticsService reservaStadisticsService;
    @Autowired
    IFFacturaStadisticsService facturaStadisticsService;
    @Autowired
    IFPlatoStadisticsService platoStadisticsService;
    @Autowired
    IFClienteStadisticsService clienteStadisticsService;
    @Autowired
    EmailClient emailClient;

    @PostMapping("/stats/reserva")
    public ResponseEntity<?> generarReservaReporte() {
        emailClient.sendReservaReporte(reservaStadisticsService.generarReporteCompleto());
        return ResponseEntity.ok().body("Reporte Generado y enviado");
    }

    @PostMapping("/stats/factura")
    public ResponseEntity<?> generarFacturaReporte() {
        emailClient.sendFacturaReporte(facturaStadisticsService.generarReporteCompleto());
        return ResponseEntity.ok().body("Reporte Generado y enviado");
    }

    @PostMapping("/stats/plato")
    public ResponseEntity<?> generarPlatoReporte() {
        emailClient.sendPlatoReporte(platoStadisticsService.generarReporteCompleto());
        return ResponseEntity.ok().body("Reporte Generado y enviado");
    }

    @PostMapping("/stats/cliente")
    public ResponseEntity<?> generarClienteReporte() {
        emailClient.sendClienteReporte(clienteStadisticsService.generarReporteCompleto());
        return ResponseEntity.ok().body("Reporte Generado y enviado");
    }
}
