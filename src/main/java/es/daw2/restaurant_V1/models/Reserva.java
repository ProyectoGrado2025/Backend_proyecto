package es.daw2.restaurant_V1.models;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Reservas")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Reserva {

    @Id @GeneratedValue
    private Long reserva_id;

    @Column(name = "reserva_fecha", nullable = false, columnDefinition = "DATETIME")
    private LocalDateTime reserva_fecha;

    @Column(name = "numero_personas", nullable = false, columnDefinition = "INTEGER")
    private Integer numero_personas;
    
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @ManyToMany
    @JoinTable(
        name = "reserva_mesa",
        joinColumns = @JoinColumn(name = "reserva_id"),
        inverseJoinColumns = @JoinColumn(name = "mesa_id")
    )
    private List<Mesa> mesas;
}