package es.daw2.restaurant_V1.dtos.stadistics.reservas;

import java.io.Serializable;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ReservasPorDiaDTO implements Serializable{
    private LocalDate dia;
    private Long totalReservas;
}
