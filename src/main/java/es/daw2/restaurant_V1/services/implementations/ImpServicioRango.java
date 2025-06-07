package es.daw2.restaurant_V1.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.rangos.RangoRequest;
import es.daw2.restaurant_V1.dtos.rangos.RangoResponse;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.models.Rango;
import es.daw2.restaurant_V1.repositories.RangoRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioRango;

@Service
public class ImpServicioRango implements IFServicioRango{

    @Autowired
    RangoRepositorio rangoRepositorio;

    @Override
    public Page<RangoResponse> getAllRangos (Pageable pageable){
        return rangoRepositorio.findAll(pageable)
                .map(this::composeRangoResponse);
    }

    @Override
    public RangoResponse getRangoById (Long id){
        Rango rango = rangoRepositorio.findById(id)
                .orElseThrow(()->new EntityNotFoundException("No se ha encontrado el Rango con ID: "+id));
        return composeRangoResponse(rango);
    }

    @Override
    public List<Rango> getAllRangosByPuntosMinimosDesc() {
        return rangoRepositorio.findAllByOrderByPuntosMinimosDesc();
    }

    @Override
    public Rango getRangoByPuntosMinimos(Long puntosMinimos) {
        Rango rango = rangoRepositorio.getRangoByPuntosMinimos(puntosMinimos)
                .orElseThrow(()-> new EntityNotFoundException("Rango no encontrado"));
        return rango;
    }

    @Override
    public Rango obtenerRangoPorPuntos (Long puntosCliente){
        List<Rango> rangos = getAllRangosByPuntosMinimosDesc();

        for (Rango rango : rangos) {
            if(puntosCliente >= rango.getPuntosMinimos()){
                return rango;
            }
        }

        throw new EntityNotFoundException("No se encontró un rango válido para los puntos: " + puntosCliente);
    }

    @Override
    public RangoResponse crearRango(RangoRequest rangoRequest) {
        Rango rango = new Rango();
        rango.setNombreRango(rangoRequest.getNombreRango());
        rango.setDescuento(rangoRequest.getDescuento());
        rango.setPuntosMinimos(rangoRequest.getPntosMinimos());

        Rango rangoGuardado = rangoRepositorio.save(rango);
        RangoResponse rangoResponse = composeRangoResponse(rangoGuardado);

        return rangoResponse;
    }

    private RangoResponse composeRangoResponse (Rango rango){
        RangoResponse rangoResponse = new RangoResponse();
        rangoResponse.setRangoId(rango.getId());
        rangoResponse.setNombreRango(rango.getNombreRango());
        rangoResponse.setPntosMinimos(rango.getPuntosMinimos());
        rangoResponse.setDescuento(rango.getDescuento());

        return rangoResponse;
    }
}