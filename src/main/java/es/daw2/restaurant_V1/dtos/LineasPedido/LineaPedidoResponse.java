package es.daw2.restaurant_V1.dtos.LineasPedido;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class LineaPedidoResponse implements Serializable{

    private String nombrePlato;
    private Integer cantidadPlato;
    private Float precioUnitario;
    private BigDecimal precioConjunto;
}
