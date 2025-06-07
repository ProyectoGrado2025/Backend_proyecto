package es.daw2.restaurant_V1.dtos.stadistics.platos;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlatoVentasDTO implements Serializable{
    
    private Long platoId;
    private String nombre;
    private Long totalVendidos;
}
