package es.daw2.restaurant_V1.config;

import org.springframework.stereotype.Component;

import es.daw2.restaurant_V1.services.interfaces.IFServicioCliente;
import es.daw2.restaurant_V1.services.interfaces.IFServicioFactura;
import es.daw2.restaurant_V1.services.interfaces.IFServicioPedido;
import es.daw2.restaurant_V1.services.interfaces.IFServicioPlato;
import es.daw2.restaurant_V1.services.interfaces.IFServicioReserva;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WorkerServiceGroup {
    
    public final IFServicioFactura FACTURA;
    public final IFServicioPedido PEDIDO;
    public final IFServicioReserva RESERVA;
    public final IFServicioCliente CLIENTE;
    public final IFServicioPlato PLATO;
}
