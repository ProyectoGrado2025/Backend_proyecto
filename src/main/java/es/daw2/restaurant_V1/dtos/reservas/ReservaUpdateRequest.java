package es.daw2.restaurant_V1.dtos.reservas;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ReservaUpdateRequest implements Serializable{

    private LocalDateTime nuevaFecha;

    private Integer nuevoNumeroPersonas;
    
    private List <Long> nuevosAlergenos;
}
