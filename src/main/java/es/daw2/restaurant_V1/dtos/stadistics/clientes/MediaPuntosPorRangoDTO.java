package es.daw2.restaurant_V1.dtos.stadistics.clientes;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MediaPuntosPorRangoDTO implements Serializable {
    private String nombreRango;
    private Double mediaPuntos;
}