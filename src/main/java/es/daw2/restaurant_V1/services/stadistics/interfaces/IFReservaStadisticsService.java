package es.daw2.restaurant_V1.services.stadistics.interfaces;

import java.time.LocalDateTime;
import java.util.List;

import es.daw2.restaurant_V1.dtos.stadistics.reservas.ContadorReservasPorEstadoDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservaStadisticsBodyDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservasPorDiaDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservasPorMesDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservasPorSemanaDTO;

public interface IFReservaStadisticsService {

    Double avgPersonasPorReserva();

    List<ContadorReservasPorEstadoDTO> contarReservasPorEstado();

    // tests
    List<ReservasPorDiaDTO> contarReservasPorDia(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<ReservasPorSemanaDTO> contarReservasPorSemana(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    List<ReservasPorMesDTO> contarReservasPorMes(LocalDateTime fechaInicio, LocalDateTime fechaFin);

    // rangos
    List<ReservasPorDiaDTO> contarReservasPorDiaUltimoMes();

    List<ReservasPorSemanaDTO> contarReservasPorSemanaUltimos3Meses();

    List<ReservasPorMesDTO> contarReservasPorMesUltimoAnio();

    public abstract ReservaStadisticsBodyDTO generarReporteCompleto();
}