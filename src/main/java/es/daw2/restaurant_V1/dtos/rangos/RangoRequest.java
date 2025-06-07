package es.daw2.restaurant_V1.dtos.rangos;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class RangoRequest implements Serializable{

    String nombreRango;

    Long pntosMinimos;
    
    BigDecimal descuento;
}
