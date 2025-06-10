package es.daw2.restaurant_V1.dtos.clientes;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClienteRangoResponse implements Serializable{

    private String nombreCliente;
    private String emailCliente;
    private String rangoCliente;
    private Long descuento;
    private Long pntosCliente;
    
}
