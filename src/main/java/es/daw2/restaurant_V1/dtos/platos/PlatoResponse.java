package es.daw2.restaurant_V1.dtos.platos;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PlatoResponse implements Serializable{

    private Long platoId;
    private String platoNombre;
    private String platoDescripcion;
    private Float platoPrecio;
    private List<String> alergenos;
    private List<String> categorias;
}
