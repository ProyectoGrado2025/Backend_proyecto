package es.daw2.restaurant_V1.services.email;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import es.daw2.restaurant_V1.dtos.reservas.ReservaResponse;

@Component
public class EmailClient {

    private final WebClient webClient;

    public EmailClient (WebClient.Builder builder){
        this.webClient = builder
                            .baseUrl("http://localhost:8081/email")
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
}
