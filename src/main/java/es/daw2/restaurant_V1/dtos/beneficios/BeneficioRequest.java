package es.daw2.restaurant_V1.dtos.beneficios;

import java.io.Serializable;

import lombok.Data;

@Data
public class BeneficioRequest implements Serializable{

    String codBeneficio;
    
    String decripBeneficio;
}