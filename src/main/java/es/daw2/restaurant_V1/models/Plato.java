package es.daw2.restaurant_V1.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Platos")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Plato {

    @Id @GeneratedValue
    private Long plato_id;

    @Column(name = "plato_nombre", nullable = false, columnDefinition = "VARCHAR(45)")
    private String plato_nombre;

    @Column(name = "plato_descripcion", nullable = false, columnDefinition = "VARCHAR(200)")
    private String plato_descripcion;

    @Column(name = "plato_precio", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Float plato_precio;

    @ManyToMany
    @JoinTable(
        name = "plato_categoria",
        joinColumns = @JoinColumn(name = "plato_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<PlatoCategoria> categorias;
}
