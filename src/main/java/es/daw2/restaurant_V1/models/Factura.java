package es.daw2.restaurant_V1.models;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Facturas")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Factura {
    
    @Id @GeneratedValue
    private Long factura_id;

    @Column(name = "factura_precio", nullable = false, columnDefinition = "INTEGER")
    private Integer factura_precio;

    @Column(name = "factura_fecha", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime factura_fecha;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "reserva_id", nullable = true)
    private Reserva reserva;

    @OneToOne
    @JoinColumn(name = "pedido_id")
    private Pedido pedido;
}
