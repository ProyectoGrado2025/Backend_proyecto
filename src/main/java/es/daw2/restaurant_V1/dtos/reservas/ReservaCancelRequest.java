package es.daw2.restaurant_V1.dtos.reservas;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReservaCancelRequest implements Serializable{
    private String clienteEmail;
}
