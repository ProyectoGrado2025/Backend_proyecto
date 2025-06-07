package es.daw2.restaurant_V1.services.fidelity;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.models.Cliente;
import es.daw2.restaurant_V1.models.Rango;
import es.daw2.restaurant_V1.repositories.ClienteRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioRango;

@Service
public class FidelityService {

    @Autowired
    private IFServicioRango servicioRango;
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    public Long getPuntosGenerados (BigDecimal totalFactura){
        Long puntosGanados = totalFactura.divideToIntegralValue(BigDecimal.valueOf(5)).longValue();
        return puntosGanados;
    }

    public void calcularFidelizacion (Cliente cliente, BigDecimal totalFactura){
        Long puntosSumados = cliente.getPuntosFidelizacion() + getPuntosGenerados(totalFactura);
        Rango rangoNuevo = servicioRango.obtenerRangoPorPuntos(puntosSumados);

        cliente.setRango(rangoNuevo);
        cliente.setPuntosFidelizacion(puntosSumados);

        clienteRepositorio.save(cliente);
    }
}
