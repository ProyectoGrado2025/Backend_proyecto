package es.daw2.restaurant_V1.services.stadistics.implementations;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.stadistics.reservas.ContadorReservasPorEstadoDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservaStadisticsBodyDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservasPorDiaDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservasPorMesDTO;
import es.daw2.restaurant_V1.dtos.stadistics.reservas.ReservasPorSemanaDTO;
import es.daw2.restaurant_V1.repositories.ReservaRepositorio;
import es.daw2.restaurant_V1.services.stadistics.interfaces.IFReservaStadisticsService;

@Service
public class ImpReservaStadisticsService implements IFReservaStadisticsService {

    @Autowired
    private ReservaRepositorio reservaRepositorio;

    @Override
    public Double avgPersonasPorReserva() {
        return reservaRepositorio.avgPersonasPorReserva();
    }

    @Override
    public List<ContadorReservasPorEstadoDTO> contarReservasPorEstado() {
        return reservaRepositorio.contarReservasPorEstado();
    }

    @Override
    public List<ReservasPorDiaDTO> contarReservasPorDia(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reservaRepositorio.contarReservasPorDia(fechaInicio, fechaFin);
    }

    @Override
    public List<ReservasPorDiaDTO> contarReservasPorDiaUltimoMes() {
        LocalDateTime inicio = LocalDate.now().minusMonths(1).atStartOfDay();
        LocalDateTime fin = LocalDate.now().plusDays(1).atStartOfDay().minusNanos(1);
        return contarReservasPorDia(inicio, fin);
    }

    @Override
    public List<ReservasPorSemanaDTO> contarReservasPorSemana(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reservaRepositorio.contarReservasPorSemana(fechaInicio, fechaFin);
    }

    @Override
    public List<ReservasPorSemanaDTO> contarReservasPorSemanaUltimos3Meses() {
        LocalDateTime inicio = LocalDateTime.now().minusMonths(3);
        LocalDateTime fin = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0).minusNanos(1);
        return contarReservasPorSemana(inicio, fin);
    }
    
    @Override
    public List<ReservasPorMesDTO> contarReservasPorMes(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
        return reservaRepositorio.contarReservasPorMes(fechaInicio, fechaFin);
    }

    @Override
    public List<ReservasPorMesDTO> contarReservasPorMesUltimoAnio() {
        LocalDateTime inicio = LocalDateTime.now().minusYears(1);
        LocalDateTime fin = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0).withSecond(0).withNano(0).minusNanos(1);
        return contarReservasPorMes(inicio, fin);
    }

    @Override
    public ReservaStadisticsBodyDTO generarReporteCompleto() {
        ReservaStadisticsBodyDTO reporte = new ReservaStadisticsBodyDTO();
        reporte.setAvgPersonasPorReserva(avgPersonasPorReserva());
        reporte.setReservasPorEstado(contarReservasPorEstado());
        reporte.setReservasPorDiaUltimoMes(contarReservasPorDiaUltimoMes());
        reporte.setReservasPorSemanaUltimos3Meses(contarReservasPorSemanaUltimos3Meses());
        reporte.setReservasPorMesUltimoAnio(contarReservasPorMesUltimoAnio());
        return reporte;
    }
}