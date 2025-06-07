package es.daw2.restaurant_V1.services.stadistics.interfaces;

import java.math.BigDecimal;

import es.daw2.restaurant_V1.dtos.stadistics.facturas.FacturaStadisticsbodyDTO;

public interface IFFacturaStadisticsService {

    BigDecimal gastoMedioGlobal();

    BigDecimal ingresoTotal();

    BigDecimal calcularIngresosDiaActual();

    BigDecimal calcularIngresosDiaAnteriorCompleto();

    BigDecimal calcularIngresosSemanaActual();

    BigDecimal calcularIngresosUltimaSemanaCompleta();

    BigDecimal calcularIngresosMesActual();

    BigDecimal calcularIngresosUltimoMesCompleto();

    BigDecimal calcularIngresosAnioActual();

    BigDecimal calcularIngresosUltimoAnioCompleto();

    FacturaStadisticsbodyDTO generarReporteCompleto();
}

