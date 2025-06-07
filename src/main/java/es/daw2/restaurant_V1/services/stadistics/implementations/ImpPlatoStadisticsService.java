package es.daw2.restaurant_V1.services.stadistics.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.stadistics.platos.PlatoStadisticsBodyDTO;
import es.daw2.restaurant_V1.dtos.stadistics.platos.PlatoVentasDTO;
import es.daw2.restaurant_V1.repositories.LineaPedidoRepositorio;
import es.daw2.restaurant_V1.services.stadistics.interfaces.IFPlatoStadisticsService;

@Service
public class ImpPlatoStadisticsService implements IFPlatoStadisticsService {
    
    @Autowired
    LineaPedidoRepositorio lineaPedidoRepositorio;

    private static final Pageable TOP_3 = PageRequest.of(0, 3);

    @Override
    public List<PlatoVentasDTO> findTop3PlatosMasVendidos() {
        return lineaPedidoRepositorio.findTop3PlatosMasVendidos(TOP_3);
    }

    @Override
    public List<PlatoVentasDTO> findTop3PlatosMenosVendidos() {
        return lineaPedidoRepositorio.findTop3PlatosMenosVendidos(TOP_3);
    }

    @Override
    public PlatoStadisticsBodyDTO generarReporteCompleto(){
        PlatoStadisticsBodyDTO stats = new PlatoStadisticsBodyDTO();
        stats.setTop3PlatosMasVendidos(findTop3PlatosMasVendidos());
        stats.setTop3PlatosMenosVendidos(findTop3PlatosMenosVendidos());

        return stats;
    }
}
