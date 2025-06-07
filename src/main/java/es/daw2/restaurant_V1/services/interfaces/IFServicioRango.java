package es.daw2.restaurant_V1.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.rangos.*;
import es.daw2.restaurant_V1.models.Rango;

public interface IFServicioRango {
    
    public abstract Rango getRangoByPuntosMinimos (Long puntosMinimos);
    public abstract RangoResponse crearRango (RangoRequest rangoRequest);
    public abstract List<Rango> getAllRangosByPuntosMinimosDesc();
    public abstract Rango obtenerRangoPorPuntos (Long puntosCliente);
    public abstract RangoResponse getRangoById (Long id);
    public abstract Page<RangoResponse> getAllRangos (Pageable pageable);
}