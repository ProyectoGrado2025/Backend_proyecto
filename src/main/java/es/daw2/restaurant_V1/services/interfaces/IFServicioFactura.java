package es.daw2.restaurant_V1.services.interfaces;

import java.util.ArrayList;
import java.util.Optional;

import es.daw2.restaurant_V1.models.Factura;


public interface IFServicioFactura {

    // Métodos a implementar
    public abstract ArrayList<Factura> getInvoices();
    public abstract Optional<Factura> getInvoiceById(Long id);
    public abstract boolean createInvoice(Factura invoice);
    public abstract boolean updateInvoice(Factura invoice, Long id);
    public abstract boolean deleteInvoice(Long id);

}
