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
import lombok.Data;

@Entity
@Table(name = "Platos")
@Data
public class Plato {

    @Id 
    @GeneratedValue
    @Column(name = "plato_id")
    private Long platoId;

    @Column(name = "plato_nombre", nullable = false, length = 150)
    private String platoNombre;

    @Column(name = "plato_descripcion", nullable = false, length = 300)
    private String platoDescripcion;

    @Column(name = "plato_precio", nullable = false, columnDefinition = "DECIMAL(10,2)")
    private Float platoPrecio;

    @ManyToMany
    @JoinTable(
        name = "plato_categoria",
        joinColumns = @JoinColumn(name = "plato_id"),
        inverseJoinColumns = @JoinColumn(name = "categoria_id")
    )
    private List<Categoria> categorias;

    @ManyToMany
    @JoinTable(
        name = "plato_alergeno",
        joinColumns = @JoinColumn(name = "plato_id"),
        inverseJoinColumns = @JoinColumn(name = "alergeno_id")
    )
    private List<Alergeno> alergenos;
}