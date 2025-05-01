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
@Table(name = "Platos_categorias")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class PlatoCategoria {

    @Id @GeneratedValue
    private Long categoria_id;

    @Column(name = "categoria_nombre", nullable = false, columnDefinition = "VARCHAR(45)")
    private String categoria_nombre;
}
