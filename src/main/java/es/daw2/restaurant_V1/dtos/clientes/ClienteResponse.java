package es.daw2.restaurant_V1.dtos.clientes;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClienteResponse implements Serializable{

    private Long clienteId;
    private String nombreCliente;
    private String emailCliente;
    private String numeroCliente;
    private String rangoCliente;
    private Long clientePuntos;
}
