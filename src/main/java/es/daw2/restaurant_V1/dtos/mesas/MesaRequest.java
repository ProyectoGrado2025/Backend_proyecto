package es.daw2.restaurant_V1.dtos.mesas;

import java.io.Serializable;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MesaRequest implements Serializable {

    @NotNull(message = "El número de mesa es obligatorio")
    private Integer mesaNumero;

    @NotNull(message = "La capacidad es obligatoria")
    private Integer mesaCapacidad;
}

