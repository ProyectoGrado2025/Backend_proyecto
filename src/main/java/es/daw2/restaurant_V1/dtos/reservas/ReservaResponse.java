package es.daw2.restaurant_V1.dtos.reservas;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ReservaResponse implements Serializable {

    private Long reservaId;
    private LocalDateTime reservaFecha;
    private LocalDateTime reservaFin;
    private Integer numeroPersonas;
    private String reservaStatus;

    private Long mesaId;
    private Integer mesaNumero;
    private Integer mesaCapacidad;

    private String clienteNombre;
    private String clienteEmail;

    private List<String> alergenos;
}