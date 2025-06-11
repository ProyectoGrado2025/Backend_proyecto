package es.daw2.restaurant_V1.services.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import es.daw2.restaurant_V1.dtos.facturas.FacturaResponse;
import es.daw2.restaurant_V1.dtos.reservas.ReservaResponse;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.ClienteStadisticsBodyDTO;
import es.daw2.restaurant_V1.dtos.stadistics.facturas.FacturaStadisticsbodyDTO;
import es.daw2.restaurant_V1.dtos.stadistics.platos.PlatoStadisticsBodyDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservaStadisticsBodyDTO;

@Component
public class EmailClient {

    private final WebClient webClient;

    public EmailClient (WebClient.Builder builder, @Value("${email.service.base-url}") String emailBaseUrl){
        this.webClient = builder
                            .baseUrl(emailBaseUrl)
                            .build();
    }

    public void sendConfirmationEmail (ReservaResponse reservaResponse){
        webClient.post()
                    .uri("/send/confirmacion")
                    .bodyValue(reservaResponse)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }

    public void sendModificationEmail (ReservaResponse reservaResponse){
        webClient.post()
                    .uri("/send/modificacion")
                    .bodyValue(reservaResponse)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }

    public void sendCancelationEmail (ReservaResponse reservaResponse){
        webClient.post()
                    .uri("/send/cancelacion")
                    .bodyValue(reservaResponse)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }

    public void sendFactura (FacturaResponse facturaResponse){
        webClient.post()
                    .uri("/send/factura")
                    .bodyValue(facturaResponse)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }

    public void sendReservaReporte (ReservaStadisticsBodyDTO stats){
        webClient.post()
                    .uri("/send/reserva/stats")
                    .bodyValue(stats)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }

    public void sendFacturaReporte (FacturaStadisticsbodyDTO stats){
        webClient.post()
                    .uri("/send/factura/stats")
                    .bodyValue(stats)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }

    public void sendPlatoReporte (PlatoStadisticsBodyDTO stats){
        webClient.post()
                    .uri("/send/plato/stats")
                    .bodyValue(stats)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }

    public void sendClienteReporte (ClienteStadisticsBodyDTO stats){
        webClient.post()
                    .uri("/send/cliente/stats")
                    .bodyValue(stats)
                    .retrieve()
                    .bodyToMono(Void.class)
                    .block();
    }
}