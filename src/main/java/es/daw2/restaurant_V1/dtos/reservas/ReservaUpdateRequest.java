package es.daw2.restaurant_V1.dtos.reservas;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Future;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class ReservaUpdateRequest implements Serializable {

    @Future(message = "La nueva fecha debe estar en el futuro")
    private LocalDateTime nuevaFecha;

    @Min(value = 1, message = "Debe haber al menos una persona")
    private Integer nuevoNumeroPersonas;

    private List<Long> nuevosAlergenos;
}
