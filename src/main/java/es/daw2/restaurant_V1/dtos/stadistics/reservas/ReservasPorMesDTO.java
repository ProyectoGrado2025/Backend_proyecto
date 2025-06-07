package es.daw2.restaurant_V1.dtos.stadistics.reservas;

import java.io.Serializable;

import lombok.Data;

@Data
public class ReservasPorMesDTO implements Serializable{
    private Integer anio;
    private Integer mes;
    private Long totalReservas;

    public ReservasPorMesDTO(Integer anio, Integer mes, Long totalReservas) {
        this.anio = anio;
        this.mes = mes;
        this.totalReservas = totalReservas;
    }
}