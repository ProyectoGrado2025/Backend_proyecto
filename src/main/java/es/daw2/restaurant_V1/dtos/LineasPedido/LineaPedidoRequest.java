package es.daw2.restaurant_V1.dtos.LineasPedido;

import java.io.Serializable;

import lombok.Data;

@Data
public class LineaPedidoRequest implements Serializable {
    
    private Long pedidoId;
    private Long platoId;
    private Integer cantidadPlato;
}
