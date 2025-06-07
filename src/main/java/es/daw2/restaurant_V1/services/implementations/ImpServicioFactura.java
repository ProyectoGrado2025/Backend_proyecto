package es.daw2.restaurant_V1.services.implementations;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.LineasPedido.LineaPedidoResponse;
import es.daw2.restaurant_V1.dtos.clientes.ClienteResponse;
import es.daw2.restaurant_V1.dtos.facturas.FacturaRequest;
import es.daw2.restaurant_V1.dtos.facturas.FacturaResponse;
import es.daw2.restaurant_V1.dtos.pedidos.PedidoResponse;
import es.daw2.restaurant_V1.exceptions.custom.ReservaAlreadyFacturadaException;
import es.daw2.restaurant_V1.models.Beneficio;
import es.daw2.restaurant_V1.models.Cliente;
import es.daw2.restaurant_V1.models.Factura;
import es.daw2.restaurant_V1.models.Reserva;
import es.daw2.restaurant_V1.models.Pedido.EstadoPedido;
import es.daw2.restaurant_V1.models.Rango;
import es.daw2.restaurant_V1.models.Reserva.ReservaStatus;
import es.daw2.restaurant_V1.models.LineaPedido;
import es.daw2.restaurant_V1.models.Pedido;
import es.daw2.restaurant_V1.repositories.FacturaRepositorio;
import es.daw2.restaurant_V1.repositories.PedidoRepositorio;
import es.daw2.restaurant_V1.repositories.ReservaRepositorio;
import es.daw2.restaurant_V1.services.email.EmailClient;
import es.daw2.restaurant_V1.services.fidelity.FidelityService;
import es.daw2.restaurant_V1.services.interfaces.IFServicioBeneficio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioFactura;
import es.daw2.restaurant_V1.services.interfaces.IFServicioLineaPedido;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ImpServicioFactura implements IFServicioFactura{

    @Autowired
    FacturaRepositorio facturaRepositorio;
    @Autowired
    ReservaRepositorio reservaRepositorio;
    @Autowired
    PedidoRepositorio pedidoRepositorio;
    @Autowired
    IFServicioLineaPedido lineaPedidoServicio;
    @Autowired
    FidelityService fidelityService;
    @Autowired
    IFServicioBeneficio beneficioServicio;
    @Autowired
    EmailClient emailClient;

    private static final Logger LOG = LoggerFactory.getLogger(ImpServicioFactura.class);
    
    @Value("${fidelizacion.minPuntosBeneficio}")
    private Long minPuntosParaBeneficio;

    @Override
    public Page<FacturaResponse> getAllFacturas(Pageable pageable){
        return facturaRepositorio.findAll(pageable)
                .map(this::composeFacturaResponse);
    }

    @Override
    public FacturaResponse getFacturaById(Long id) {
        Factura facturaFromDb = facturaRepositorio.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada con ID: " + id));
                
        return composeFacturaResponse(facturaFromDb);
    }

    /**
     * Crea una factura asociada a un pedido y reserva.
     * Permite facturar reservas incluso si están en estado EXPIRADA,
     * actualizando el estado de la reserva a FACTURADA para evitar
     * cambios futuros y reflejar que la reserva fue utilizada
     */
    @Override
    @Transactional
    public FacturaResponse crearFactura(FacturaRequest facturaRequest) {
        Factura factura = new Factura();

        Pedido pedidoFromDb = pedidoRepositorio.findById(facturaRequest.getPedidoId())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + facturaRequest.getPedidoId()));

        if (pedidoFromDb.getEstado() != EstadoPedido.ABIERTO) {
            throw new IllegalStateException("El pedido con ID ["+facturaRequest.getPedidoId()+"] ya ha sido facturado y no se puede modificar.");
        }

        pedidoFromDb.setEstado(EstadoPedido.FACTURADO);
        Pedido pedidoFacturado = pedidoRepositorio.saveAndFlush(pedidoFromDb);

        Reserva reservaFromDb = pedidoFacturado.getReserva();

        if(reservaFromDb.getReservaStatus() == ReservaStatus.FACTURADA){
            throw new ReservaAlreadyFacturadaException("La RESERVA con ID ["+reservaFromDb.getReservaId()+"] ya ha sido facturada y no se puede volver a facturar.");
        }
        
        reservaFromDb.setReservaStatus(ReservaStatus.FACTURADA);
        Reserva reservaActualizada = reservaRepositorio.saveAndFlush(reservaFromDb);

        Cliente clienteFromDb = reservaActualizada.getCliente();

        factura.setReserva(reservaActualizada);
        factura.setCliente(clienteFromDb);
        factura.setFormaPago(facturaRequest.getFormaPago());
        factura.setFacturaFecha(LocalDateTime.now());
        factura.setPedido(pedidoFacturado);

        BigDecimal precioTotal = pedidoFacturado.getLineasPedido().stream()
                .map(LineaPedido::getPrecioTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Long puntosGenerados = fidelityService.getPuntosGenerados(precioTotal);
        factura.setPuntosGenerados(puntosGenerados);

        // rango y descuento antes de que pueda cambiar
        Rango rangoActual = clienteFromDb.getRango();
        BigDecimal descuentoEntero = rangoActual.getDescuento().setScale(0, RoundingMode.DOWN);
        BigDecimal descuento = descuentoEntero.divide(BigDecimal.valueOf(100));

        BigDecimal porcentaje = BigDecimal.ONE.subtract(descuento);
        BigDecimal totalConDescuento = precioTotal.multiply(porcentaje).setScale(2, RoundingMode.HALF_UP);

        factura.setFacturaPrecioTotal(totalConDescuento);
        factura.setDescuentoAplicado(descuentoEntero);
        factura.setNombreRangoAplicado(rangoActual.getNombreRango());

        // cálculo del sistema de fidelización que puede cambiar el rango del cliente
        fidelityService.calcularFidelizacion(clienteFromDb, precioTotal);

        if (clienteFromDb.getPuntosFidelizacion() >= minPuntosParaBeneficio) {
            Beneficio beneficioRandom = beneficioServicio.getBeneficioRandom();
            factura.setBeneficio(beneficioRandom);
        }

        Factura facturaGuardada = facturaRepositorio.save(factura);
        FacturaResponse facturaResponse = composeFacturaResponse(facturaGuardada);

        try {
            emailClient.sendFactura(facturaResponse);
        } catch (Exception e) {
            LOG.warn("No se pudo enviar el correo con la factura con el ID {}: {}", facturaGuardada.getFacturaId(), e.getMessage());
        }

        return facturaResponse;
    }


    private FacturaResponse composeFacturaResponse(Factura facturaGuardada) {
        FacturaResponse facturaResponse = new FacturaResponse();

        // --- Datos del Cliente ---
        Cliente clienteFromFactura = facturaGuardada.getCliente();
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setClienteId(clienteFromFactura.getId());
        clienteResponse.setNombreCliente(clienteFromFactura.getClienteNombre());
        clienteResponse.setEmailCliente(clienteFromFactura.getEmail());
        clienteResponse.setNumeroCliente(clienteFromFactura.getClienteTlfn());
        facturaResponse.setClienteResponse(clienteResponse);

        // --- Datos del Pedido ---
        Pedido pedido = facturaGuardada.getPedido();
        List<LineaPedido> lineasPedido = pedido.getLineasPedido();
        List<LineaPedidoResponse> lineasPedidoResponse = lineaPedidoServicio.crearLineaPedidoResponse(lineasPedido);
        
        PedidoResponse pedidoResponse = new PedidoResponse();
        pedidoResponse.setPedidoId(pedido.getPedidoId());
        pedidoResponse.setLineasPedidoResponse(lineasPedidoResponse);
        facturaResponse.setPedidoResponse(pedidoResponse);

        // --- Otros datos de la factura ---
        facturaResponse.setFacturaId(facturaGuardada.getFacturaId());
        facturaResponse.setFechaFactura(facturaGuardada.getFacturaFecha());
        facturaResponse.setFacturaPrecio(facturaGuardada.getFacturaPrecioTotal());
        facturaResponse.setFormaPago(facturaGuardada.getFormaPago().name());
        facturaResponse.setReservaId(facturaGuardada.getReserva().getReservaId());

        facturaResponse.setBeneficioCodigo(
            facturaGuardada.getBeneficio() != null 
                ? facturaGuardada.getBeneficio().getCodigo() 
                : "SIN_BENEFICIO"
        );

        facturaResponse.setDescuentoAplicado(facturaGuardada.getDescuentoAplicado());
        facturaResponse.setPuntosGenerados(facturaGuardada.getPuntosGenerados());
        facturaResponse.setNombreRangoAplicado(facturaGuardada.getNombreRangoAplicado());

        return facturaResponse;
    }
}