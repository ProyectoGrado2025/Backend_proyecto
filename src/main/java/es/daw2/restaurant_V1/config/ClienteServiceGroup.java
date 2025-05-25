package es.daw2.restaurant_V1.config;

import org.springframework.stereotype.Component;

import es.daw2.restaurant_V1.services.interfaces.IFServicioReserva;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ClienteServiceGroup {

    public final IFServicioReserva RESERVA;
    
}
