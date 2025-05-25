package es.daw2.restaurant_V1.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.facturas.FacturaRequest;
import es.daw2.restaurant_V1.dtos.facturas.FacturaResponse;

public interface IFServicioFactura {

    public abstract FacturaResponse crearFactura (FacturaRequest facturaRequest);
    public abstract Page<FacturaResponse> getAllFacturas(Pageable pageable);
    public abstract FacturaResponse getFacturaById(Long id);
}
