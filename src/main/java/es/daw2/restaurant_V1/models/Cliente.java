package es.daw2.restaurant_V1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
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

    @Id @GeneratedValue
    private Long id;

    @Column(name = "cliente_nombre", nullable = false, columnDefinition = "VARCHAR(45)")
    private String cliente_nombre;

    @Column(name = "cliente_email", nullable = false, columnDefinition =  "VARCHAR(45)")
    private String cliente_email;

    @Column(name = "cliente_tlfn", nullable = false, columnDefinition = "VARCHAR(45)")
    private String cliente_tlfn;
}
