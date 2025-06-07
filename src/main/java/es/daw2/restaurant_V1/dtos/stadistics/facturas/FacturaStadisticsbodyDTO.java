package es.daw2.restaurant_V1.dtos.stadistics.facturas;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class FacturaStadisticsbodyDTO implements Serializable{

    private BigDecimal gastoMedioGlobal;
    private BigDecimal ingresoTotal;

    private BigDecimal ingresosDiaActual;
    private BigDecimal ingresosDiaAnteriorCompleto;

    private BigDecimal ingresosSemanaActual;
    private BigDecimal ingresosUltimaSemanaCompleta;

    private BigDecimal ingresosMesActual;
    private BigDecimal ingresosUltimoMesCompleto;

    private BigDecimal ingresosAnioActual;
    private BigDecimal ingresosUltimoAnioCompleto;
}