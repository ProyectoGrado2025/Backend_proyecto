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
@Table(name = "Mesas")

@Data
@AllArgsConstructor
@NoArgsConstructor

public class Mesa {

    @Id @GeneratedValue
    private Long mesa_id;

    @Column(name = "mesa_numero", nullable = false, columnDefinition = "INTEGER")
    private Integer mesa_numero;

    @Column(name = "mesa_capacidad", nullable = false, columnDefinition = "INTEGER")
    private Integer mesa_capacidad;

    @Column(name = "mesa_disponibilidad", nullable = false, columnDefinition = "BOOLEAN")
    private Boolean mesa_disponibilidad;
}
