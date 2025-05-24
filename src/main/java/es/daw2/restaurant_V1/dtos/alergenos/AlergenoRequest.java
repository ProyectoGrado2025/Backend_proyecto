package es.daw2.restaurant_V1.dtos.alergenos;

import java.io.Serializable;

import lombok.Data;

@Data
public class AlergenoRequest implements Serializable{

    private String nombreAlergeno;
    private String descripcionAlergeno;
}
