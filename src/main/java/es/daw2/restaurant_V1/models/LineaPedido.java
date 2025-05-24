package es.daw2.restaurant_V1.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "linea_pedido")
public class LineaPedido {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "plato_id", nullable = false)
    private Plato plato;

    @Column(name = "cantidad_plato", nullable = false)
    private Integer cantidad;

    @Column(name = "precio_unitario_plato", nullable = false, precision = 10, scale = 2)
    private BigDecimal precioUnitario;

    public BigDecimal getPrecioTotal() {
        return precioUnitario.multiply(BigDecimal.valueOf(cantidad));
    }
}

/*
 * Plato más vendido
 * Plato menos vendido
 * Gasto medio de un cliente
 * Qué plato se vende más por fecha
 */
