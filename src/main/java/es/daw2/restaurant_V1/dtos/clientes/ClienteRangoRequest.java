package es.daw2.restaurant_V1.dtos.clientes;

import java.io.Serializable;

import lombok.Data;

@Data
public class ClienteRangoRequest implements Serializable{
    private String emailCliente;
}