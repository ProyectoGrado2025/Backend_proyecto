package es.daw2.restaurant_V1.models;

import java.util.List;

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
@Table(name = "Pedidos")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Pedido {

    @Id @GeneratedValue
    private Long pedido_id;

    @ManyToMany
    @JoinTable(
        name = "pedido_plato",
        joinColumns = @JoinColumn(name = "pedido_id"),
        inverseJoinColumns = @JoinColumn(name = "plato_id")
    )
    private List<Plato> platos;
}
