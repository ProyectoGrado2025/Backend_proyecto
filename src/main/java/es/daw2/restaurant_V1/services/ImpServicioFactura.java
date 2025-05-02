package es.daw2.restaurant_V1.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.models.Factura;
import es.daw2.restaurant_V1.repositories.FacturaRepositorio;

@Service
public class ImpServicioFactura implements IFServicioFactura{

    @Autowired
    FacturaRepositorio invoiceRepository;

    @Override
    public ArrayList<Factura> getInvoices() {
        return (ArrayList<Factura>) invoiceRepository.findAll(); 
    }

    @Override
    public Optional<Factura> getInvoiceById(Long id) {
        return invoiceRepository.findById(id);
    }

    @Override
    public boolean createInvoice(Factura invoice) {
        if (invoice != null){
            invoiceRepository.save(invoice);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateInvoice(Factura invoice, Long id) {
        Optional<Factura> invoiceContainer = invoiceRepository.findById(id);

        if (invoiceContainer.isPresent()){

            Factura existingInvoice = invoiceContainer.get();
            existingInvoice.setCliente(invoice.getCliente());
            existingInvoice.setFactura_fecha(invoice.getFactura_fecha());
            existingInvoice.setFactura_precio(invoice.getFactura_precio());
            existingInvoice.setPedido(invoice.getPedido());
            existingInvoice.setReserva(invoice.getReserva());

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteInvoice(Long id) {
        Optional<Factura> invoiceContainer = invoiceRepository.findById(id);

        if(invoiceContainer.isPresent()){

            invoiceRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
