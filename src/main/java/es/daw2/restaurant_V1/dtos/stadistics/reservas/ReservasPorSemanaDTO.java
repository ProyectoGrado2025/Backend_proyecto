package es.daw2.restaurant_V1.dtos.stadistics.reservas;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReservasPorSemanaDTO implements Serializable{
    private Integer anio;
    private Integer semana;
    private Long totalReservas;

    public ReservasPorSemanaDTO(Integer anio, Integer semana, Long totalReservas) {
        this.anio = anio;
        this.semana = semana;
        this.totalReservas = totalReservas;
    }
}
