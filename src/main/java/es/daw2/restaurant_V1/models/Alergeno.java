package es.daw2.restaurant_V1.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "Alergenos")
@Data
public class Alergeno {

    @Id
    @GeneratedValue
    @Column(name = "alergeno_id")
    private Long alergenoId;

    @Column(name = "nombre_alergeno", nullable = false, unique = true, length = 50)
    private String nombreAlergeno;

    @Column(name = "descripcion", columnDefinition = "TEXT", nullable = false)
    private String descripcionAlergeno;
}