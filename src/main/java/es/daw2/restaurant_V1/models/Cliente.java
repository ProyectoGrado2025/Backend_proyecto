package es.daw2.restaurant_V1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Clientes")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Cliente {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "cliente_nombre", nullable = false, length = 45)
    private String clienteNombre;

    @Column(name = "cliente_email", nullable = false, length = 45)
    private String email;

    @Column(name = "cliente_tlfn", nullable = false, length = 45)
    private String clienteTlfn;

    @Column(name = "puntos_fidelizacion", nullable = false)
    private Long puntosFidelizacion = 0L;

    @ManyToOne
    @JoinColumn(name = "rango_id")
    private Rango rango;
}
