package es.daw2.restaurant_V1.dtos.beneficios;

import java.io.Serializable;

import lombok.Data;

@Data
public class BeneficioResponse implements Serializable{

    Long idBeneficio;
    
    String codBeneficio;
    
    String decripBeneficio;

    String status;
}
