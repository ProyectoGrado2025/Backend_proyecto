package es.daw2.restaurant_V1.dtos.reservas;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ReservaRequest implements Serializable {

    @NotNull
    private LocalDateTime reservaFecha;

    @NotNull
    @Min(1)
    private Integer numeroPersonas;

    @NotBlank
    @Email
    private String clienteEmail;

    @NotBlank
    private String clienteNombre;

    @NotBlank
    private String clienteTlfn;

    private List<Long> alergenosIds;
}