package es.daw2.restaurant_V1.dtos.rangos;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class RangoResponse implements Serializable{

    Long rangoId;

    String nombreRango;

    Long pntosMinimos;
    
    BigDecimal descuento;
}