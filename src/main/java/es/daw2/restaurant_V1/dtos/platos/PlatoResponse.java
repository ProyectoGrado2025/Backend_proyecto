package es.daw2.restaurant_V1.dtos.platos;

import java.io.Serializable;
import java.util.List;

import es.daw2.restaurant_V1.models.Alergeno;
import es.daw2.restaurant_V1.models.Categoria;
import lombok.Data;

@Data
public class PlatoResponse implements Serializable{

    private String platoNombre;
    private String platoDecripcion;
    private Float platoPrecio;
    private List<Alergeno> alergenos;
    private List<Categoria> categorias;

}
