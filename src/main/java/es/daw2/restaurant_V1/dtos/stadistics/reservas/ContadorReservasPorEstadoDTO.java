package es.daw2.restaurant_V1.dtos.stadistics.reservas;

import java.io.Serializable;

import es.daw2.restaurant_V1.models.Reserva.ReservaStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ContadorReservasPorEstadoDTO implements Serializable{
    
    private ReservaStatus reservaStatus;
    private Long cantidad;
}
