package es.daw2.restaurant_V1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "beneficios")
@Data
public class Beneficio {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;

    @Column(name = "descripcion", nullable = false, length = 255)
    private String descripcion;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private BeneficioStatus status;

    public enum BeneficioStatus{
        HABILITADO,
        DESHABILITADO
    }
}