package es.daw2.restaurant_V1.dtos.categoria;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class CategoriaRequest implements Serializable{

    private String nombreCategoria;
    
    private String descripcion;

    private List<Long> platosIds;
}
