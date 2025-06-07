package es.daw2.restaurant_V1.services.stadistics.implementations;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.stadistics.facturas.FacturaStadisticsbodyDTO;
import es.daw2.restaurant_V1.repositories.FacturaRepositorio;
import es.daw2.restaurant_V1.services.stadistics.interfaces.IFFacturaStadisticsService;

@Service
public class ImpFacturaStadisticsService implements IFFacturaStadisticsService {

    @Autowired
    FacturaRepositorio facturaRepositorio;

    @Override
    public BigDecimal gastoMedioGlobal() {
        return facturaRepositorio.GastoMedioGlobal();
    }

    @Override
    public BigDecimal ingresoTotal() {
        return facturaRepositorio.IngresoTotal();
    }

    // ingresos desde inicio de hoy hasta ahora (día en curso)
    @Override
    public BigDecimal calcularIngresosDiaActual() {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fin = LocalDateTime.now();
        return facturaRepositorio.calcularIngresosEntreFechas(inicio, fin);
    }

    // ingresos del día completo anterior (ayer)
    @Override
    public BigDecimal calcularIngresosDiaAnteriorCompleto() {
        LocalDate diaAnterior = LocalDate.now().minusDays(1);
        LocalDateTime inicio = diaAnterior.atStartOfDay();
        LocalDateTime fin = diaAnterior.atTime(23, 59, 59);
        return facturaRepositorio.calcularIngresosEntreFechas(inicio, fin);
    }

    // ingresos desde primer día de la semana actual hasta ahora (semana en curso)
    @Override
    public BigDecimal calcularIngresosSemanaActual() {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        LocalDate hoy = LocalDate.now();
        LocalDate primerDiaSemana = hoy.with(weekFields.dayOfWeek(), 1);
        LocalDateTime inicio = primerDiaSemana.atStartOfDay();
        LocalDateTime fin = LocalDateTime.now();
        return facturaRepositorio.calcularIngresosEntreFechas(inicio, fin);
    }

    // ingresos de la semana anterior completa, últimos 7 días cerrados, desde mismo día semana pasada hasta el día anterior a hoy
    @Override
    public BigDecimal calcularIngresosUltimaSemanaCompleta() {
        LocalDate hoy = LocalDate.now();

        //  lunes de la semana pasada
        LocalDate inicio = hoy.minusWeeks(1).with(DayOfWeek.MONDAY);
        // domingo de esa semana
        LocalDate fin = inicio.plusDays(6);

        LocalDateTime inicioFecha = inicio.atStartOfDay();
        LocalDateTime finFecha = fin.atTime(23, 59, 59);

        return facturaRepositorio.calcularIngresosEntreFechas(inicioFecha, finFecha);
    }

    // ingresos desde el primer día del mes actual hasta ahora (mes en curso)
    @Override
    public BigDecimal calcularIngresosMesActual() {
        LocalDate hoy = LocalDate.now();
        LocalDate primerDiaMes = hoy.withDayOfMonth(1);
        LocalDateTime inicio = primerDiaMes.atStartOfDay();
        LocalDateTime fin = LocalDateTime.now();
        return facturaRepositorio.calcularIngresosEntreFechas(inicio, fin);
    }

    // ingresos del mes anterior completo
    @Override
    public BigDecimal calcularIngresosUltimoMesCompleto() {
        LocalDate hoy = LocalDate.now();
        LocalDate primerDiaUltimoMes = hoy.minusMonths(1).withDayOfMonth(1);
        LocalDate ultimoDiaUltimoMes = hoy.withDayOfMonth(1).minusDays(1);
        LocalDateTime inicio = primerDiaUltimoMes.atStartOfDay();
        LocalDateTime fin = ultimoDiaUltimoMes.atTime(23, 59, 59);
        return facturaRepositorio.calcularIngresosEntreFechas(inicio, fin);
    }

    // ingresos desde primer día del año actual hasta ahora (año en curso)
    @Override
    public BigDecimal calcularIngresosAnioActual() {
        LocalDate hoy = LocalDate.now();
        LocalDate primerDiaAnio = hoy.withDayOfYear(1);
        LocalDateTime inicio = primerDiaAnio.atStartOfDay();
        LocalDateTime fin = LocalDateTime.now();
        return facturaRepositorio.calcularIngresosEntreFechas(inicio, fin);
    }

    // ingresos del año anterior completo
    @Override
    public BigDecimal calcularIngresosUltimoAnioCompleto() {
        LocalDate hoy = LocalDate.now();
        LocalDate primerDiaUltimoAnio = hoy.minusYears(1).withDayOfYear(1);
        LocalDate ultimoDiaUltimoAnio = hoy.withDayOfYear(1).minusDays(1);
        LocalDateTime inicio = primerDiaUltimoAnio.atStartOfDay();
        LocalDateTime fin = ultimoDiaUltimoAnio.atTime(23, 59, 59);
        return facturaRepositorio.calcularIngresosEntreFechas(inicio, fin);
    }

    @Override
    public FacturaStadisticsbodyDTO generarReporteCompleto() {
        FacturaStadisticsbodyDTO stats = new FacturaStadisticsbodyDTO();

        stats.setGastoMedioGlobal(this.gastoMedioGlobal());
        stats.setIngresoTotal(this.ingresoTotal());

        stats.setIngresosDiaActual(this.calcularIngresosDiaActual());
        stats.setIngresosDiaAnteriorCompleto(this.calcularIngresosDiaAnteriorCompleto());

        stats.setIngresosSemanaActual(this.calcularIngresosSemanaActual());
        stats.setIngresosUltimaSemanaCompleta(this.calcularIngresosUltimaSemanaCompleta());

        stats.setIngresosMesActual(this.calcularIngresosMesActual());
        stats.setIngresosUltimoMesCompleto(this.calcularIngresosUltimoMesCompleto());

        stats.setIngresosAnioActual(this.calcularIngresosAnioActual());
        stats.setIngresosUltimoAnioCompleto(this.calcularIngresosUltimoAnioCompleto());

        return stats;
    }
}
