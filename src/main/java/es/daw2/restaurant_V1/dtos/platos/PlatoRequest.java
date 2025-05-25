package es.daw2.restaurant_V1.dtos.platos;

import java.io.Serializable;
import java.util.List;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlatoRequest implements Serializable{

    @NotNull
    private String platoNombre;

    @NotNull
    private String platoDescripcion;

    @NotNull
    private Float platoPrecio;

    @NotNull
    private List<Long> alergenos;

    private List<Long> categorias;
}