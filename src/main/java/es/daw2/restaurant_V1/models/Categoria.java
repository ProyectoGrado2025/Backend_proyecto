package es.daw2.restaurant_V1.models;

import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Categorias")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Categoria {

    @Id @GeneratedValue
    @Column(name = "categoria_id")
    private Long categoriaId;

    @Column(name = "categoria_nombre", nullable = false, columnDefinition = "VARCHAR(50)")
    private String categoriaNombre;
    
    @Column(name = "descripcion", columnDefinition = "VARCHAR(255)")
    private String descripcion;

    @ManyToMany(mappedBy = "categorias")
    private List<Plato> platos;
}