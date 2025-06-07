package es.daw2.restaurant_V1.services.stadistics.interfaces;

import java.math.BigDecimal;
import java.util.List;

import es.daw2.restaurant_V1.dtos.stadistics.clientes.ClientePuntosDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.ClienteStadisticsBodyDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.DistribucionClientesPorRangoDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.GastoMedioClienteDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.MediaPuntosPorRangoDTO;

public interface IFClienteStadisticsService {

    public abstract List<GastoMedioClienteDTO> gastoMedioCliente();
    public abstract List<DistribucionClientesPorRangoDTO> contarClientesPorRango();
    public abstract List<MediaPuntosPorRangoDTO> mediaPuntosPorRango();
    public abstract List<ClientePuntosDTO> topClientesPorPuntos();
    public abstract BigDecimal gastoMedioPorClienteId (Long id);
    public abstract Long totalPuntosFidelizacion();
    public abstract ClienteStadisticsBodyDTO generarReporteCompleto();
}
