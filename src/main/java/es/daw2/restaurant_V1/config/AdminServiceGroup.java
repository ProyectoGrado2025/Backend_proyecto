package es.daw2.restaurant_V1.config;

import org.springframework.stereotype.Component;

import es.daw2.restaurant_V1.services.interfaces.IFServicioAlergeno;
import es.daw2.restaurant_V1.services.interfaces.IFServicioBeneficio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioCliente;
import es.daw2.restaurant_V1.services.interfaces.IFServicioFactura;
import es.daw2.restaurant_V1.services.interfaces.IFServicioMesa;
import es.daw2.restaurant_V1.services.interfaces.IFServicioPedido;
import es.daw2.restaurant_V1.services.interfaces.IFServicioPlato;
import es.daw2.restaurant_V1.services.interfaces.IFServicioRango;
import es.daw2.restaurant_V1.services.interfaces.IFServicioCategoria;
import es.daw2.restaurant_V1.services.interfaces.IFServicioReserva;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class AdminServiceGroup {

    public final IFServicioReserva RESERVA;
    public final IFServicioCliente CLIENTE;
    public final IFServicioFactura FACTURA;
    public final IFServicioPedido PEDIDO;
    public final IFServicioPlato PLATO;
    public final IFServicioCategoria CATEGORIA;
    public final IFServicioMesa MESA;
    public final IFServicioAlergeno ALERGENO;
    public final IFServicioBeneficio BENEFICIO;
    public final IFServicioRango RANGO;
}
