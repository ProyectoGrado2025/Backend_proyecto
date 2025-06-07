package es.daw2.restaurant_V1.dtos.stadistics.clientes;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientePuntosDTO implements Serializable{
    private Long id;
    private String nombre; 
    private Long puntos; 
    private String nombreRango;
}