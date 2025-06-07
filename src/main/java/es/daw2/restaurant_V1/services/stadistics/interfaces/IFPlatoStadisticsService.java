package es.daw2.restaurant_V1.services.stadistics.interfaces;

import java.util.List;

import es.daw2.restaurant_V1.dtos.stadistics.platos.PlatoStadisticsBodyDTO;
import es.daw2.restaurant_V1.dtos.stadistics.platos.PlatoVentasDTO;

public interface IFPlatoStadisticsService {

    public abstract List<PlatoVentasDTO> findTop3PlatosMasVendidos();
    public abstract List<PlatoVentasDTO> findTop3PlatosMenosVendidos();
    public abstract PlatoStadisticsBodyDTO generarReporteCompleto();
}
