package es.daw2.restaurant_V1.dtos.categoria;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CategoriaResponse implements Serializable{

    private Long categoriaId;
    private String categoriaNombre;
    private String categoriaDescrip;
    private List<String> platos;
}
