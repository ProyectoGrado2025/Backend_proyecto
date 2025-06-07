package es.daw2.restaurant_V1.models;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "rangos")
@Data
public class Rango {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "nombre", nullable = false, unique = true, length = 45)
    private String nombreRango;

    @Column(name = "puntos_minimos", nullable = false)
    private Long puntosMinimos;

    @Column(name = "descuento", nullable = false, precision = 5, scale = 2)
    private BigDecimal descuento;
}