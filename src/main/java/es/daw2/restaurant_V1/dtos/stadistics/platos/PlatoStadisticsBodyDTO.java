package es.daw2.restaurant_V1.dtos.stadistics.platos;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class PlatoStadisticsBodyDTO implements Serializable{
    
    List<PlatoVentasDTO> top3PlatosMasVendidos;
    List<PlatoVentasDTO> top3PlatosMenosVendidos;
}
