package es.daw2.restaurant_V1.models;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Facturas")

@Data
public class Factura {
    
    @Id
    @GeneratedValue
    private Long facturaId;

    @Column(name = "factura_fecha", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime facturaFecha;

    @Column(name = "factura_precio", nullable = false, precision = 10, scale = 2)
    private BigDecimal facturaPrecioTotal;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pago", nullable = false)
    private FormaPago formaPago;

    public enum FormaPago {
        EFECTIVO,
        TARJETA,
        BIZUM,
        OTRO
    }

    @ManyToOne
    @JoinColumn(name = "cliente_id", nullable = false)
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = false)
    private Reserva reserva;

    @OneToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(name = "beneficio_id")
    private Beneficio beneficio;
        
    @Column(name = "puntos_generados")
    private Long puntosGenerados;

    @Column(name = "descuento_aplicado", precision = 5, scale = 2, nullable = false)
    private BigDecimal descuentoAplicado;

    @Column(name = "nombre_rango_aplicado", nullable = false)
    private String nombreRangoAplicado;
}