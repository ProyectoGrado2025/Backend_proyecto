package es.daw2.restaurant_V1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
    private Long mesaId;

    @Column(name = "mesa_numero", nullable = false, columnDefinition = "INTEGER", unique = true)
    private Integer mesaNumero;

    @Column(name = "mesa_capacidad", nullable = false, columnDefinition = "INTEGER")
    private Integer mesaCapacidad;

    @Enumerated(EnumType.STRING)
    @Column(name = "mesa_disponibilidad", nullable = false)
    private MesaStatus mesaDisponibilidad;

    public enum MesaStatus{
        DISPONIBLE,
        RESERVADA,
        FUERA_DE_SERVICIO
    }
}
