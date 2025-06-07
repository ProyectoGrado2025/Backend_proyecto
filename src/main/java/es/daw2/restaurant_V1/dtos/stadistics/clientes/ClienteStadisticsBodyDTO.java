package es.daw2.restaurant_V1.dtos.stadistics.clientes;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class ClienteStadisticsBodyDTO implements Serializable {
    
    private List<GastoMedioClienteDTO> gastoMedioClientes;
    private List<DistribucionClientesPorRangoDTO> distribucionClientesPorRango;
    private List<MediaPuntosPorRangoDTO> mediaPuntosPorRango;
    private List<ClientePuntosDTO> topClientesPorPuntos;
    private Long totalPuntosFidelizacion;
}