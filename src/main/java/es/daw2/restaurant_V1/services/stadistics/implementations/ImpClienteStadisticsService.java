package es.daw2.restaurant_V1.services.stadistics.implementations;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.stadistics.clientes.ClientePuntosDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.ClienteStadisticsBodyDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.DistribucionClientesPorRangoDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.GastoMedioClienteDTO;
import es.daw2.restaurant_V1.dtos.stadistics.clientes.MediaPuntosPorRangoDTO;
import es.daw2.restaurant_V1.repositories.ClienteRepositorio;
import es.daw2.restaurant_V1.repositories.FacturaRepositorio;
import es.daw2.restaurant_V1.services.stadistics.interfaces.IFClienteStadisticsService;

@Service
public class ImpClienteStadisticsService implements IFClienteStadisticsService{

    @Autowired
    FacturaRepositorio facturaRepositorio;
    @Autowired
    ClienteRepositorio clienteRepositorio;

    @Override
    public List<GastoMedioClienteDTO> gastoMedioCliente(){
        return facturaRepositorio.GastoMedioPorCliente();
    }

    @Override
    public List<DistribucionClientesPorRangoDTO> contarClientesPorRango (){
        return clienteRepositorio.contarClientesPorRango();
    }

    @Override
    public List<MediaPuntosPorRangoDTO> mediaPuntosPorRango(){
        return clienteRepositorio.mediaPuntosPorRango();
    }

    @Override
    public List<ClientePuntosDTO> topClientesPorPuntos(){
        Pageable top10 = PageRequest.of(0,10);
        return clienteRepositorio.topClientesPorPuntos(top10);
    }

    @Override
    public BigDecimal gastoMedioPorClienteId (Long id){
        return facturaRepositorio.GastoMedioPorClienteId(id);
    }

    @Override
    public Long totalPuntosFidelizacion(){
        return clienteRepositorio.totalPuntosFidelizacion();
    }

    @Override
    public ClienteStadisticsBodyDTO generarReporteCompleto() {
        ClienteStadisticsBodyDTO stats = new ClienteStadisticsBodyDTO();

        stats.setGastoMedioClientes(gastoMedioCliente());
        stats.setDistribucionClientesPorRango(contarClientesPorRango());
        stats.setMediaPuntosPorRango(mediaPuntosPorRango());
        stats.setTopClientesPorPuntos(topClientesPorPuntos());
        stats.setTotalPuntosFidelizacion(totalPuntosFidelizacion());

        return stats;
    }
}