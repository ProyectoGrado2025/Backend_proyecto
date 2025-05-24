package es.daw2.restaurant_V1.dtos.mesas;

import java.io.Serializable;

import lombok.Data;

@Data
public class MesaResponse implements Serializable {

    private Long mesaId;
    private Integer mesaNumero;
    private Integer mesaCapacidad;
    private String mesaStatus;
}