package es.daw2.restaurant_V1.services.implementations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.LineasPedido.LineaPedidoResponse;
import es.daw2.restaurant_V1.dtos.clientes.ClienteResponse;
import es.daw2.restaurant_V1.dtos.facturas.FacturaRequest;
import es.daw2.restaurant_V1.dtos.facturas.FacturaResponse;
import es.daw2.restaurant_V1.dtos.pedidos.PedidoResponse;
import es.daw2.restaurant_V1.models.Cliente;
import es.daw2.restaurant_V1.models.Factura;
import es.daw2.restaurant_V1.models.Reserva;
import es.daw2.restaurant_V1.models.Pedido.EstadoPedido;
import es.daw2.restaurant_V1.models.Reserva.ReservaStatus;
import es.daw2.restaurant_V1.models.LineaPedido;
import es.daw2.restaurant_V1.models.Pedido;
import es.daw2.restaurant_V1.repositories.FacturaRepositorio;
import es.daw2.restaurant_V1.repositories.PedidoRepositorio;
import es.daw2.restaurant_V1.repositories.ReservaRepositorio;
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
     * cambios futuros y reflejar que la reserva fue utilizada.
     */
    @Override
    @Transactional
    public FacturaResponse crearFactura(FacturaRequest facturaRequest) {
        Factura factura = new Factura();

        // pedido asociado desde la base de datos, excepción si no existe
        Pedido pedidoFromDb = pedidoRepositorio.findById(facturaRequest.getPedidoId())
                .orElseThrow(() -> new EntityNotFoundException("Pedido no encontrado con ID: " + facturaRequest.getPedidoId()));

        if (pedidoFromDb.getEstado() != EstadoPedido.ABIERTO) {
            throw new IllegalStateException("El pedido ya ha sido facturado y no se puede modificar.");
        }

        // se marca el pedido como facturado y se guarda el cambio
        pedidoFromDb.setEstado(EstadoPedido.FACTURADO);
        Pedido pedidoFacturado = pedidoRepositorio.saveAndFlush(pedidoFromDb);;

        // reserva asociada al pedido
        Reserva reservaFromDb = pedidoFacturado.getReserva();
        reservaFromDb.setReservaStatus(ReservaStatus.FACTURADA);
        Reserva reservaActualizada = reservaRepositorio.saveAndFlush(reservaFromDb);

        // cliente asociado desde la reserva
        Cliente clienteFromDb = reservaActualizada.getCliente();

        // se asocia la reserva y el cliente a la factura
        factura.setReserva(reservaActualizada);
        factura.setCliente(clienteFromDb);

        // forma de pago
        factura.setFormaPago(facturaRequest.getFormaPago());

        // fecha actual a la factura
        factura.setFacturaFecha(LocalDateTime.now());

        // pedido facturado a la factura
        factura.setPedido(pedidoFacturado);

        // calculo del precio total sumando los precios de cada línea del pedido
        BigDecimal precioTotal = pedidoFacturado.getLineasPedido().stream()
                .map(LineaPedido::getPrecioTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        factura.setFacturaPrecioTotal(precioTotal);

        Factura facturaGuardada = facturaRepositorio.save(factura);

        return composeFacturaResponse(facturaGuardada);
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
        facturaResponse.setNombreCliente(clienteFromFactura.getClienteNombre());

        // --- Datos del Pedido ---
        PedidoResponse pedidoResponse = new PedidoResponse();
        List<LineaPedido> lineasPedido = facturaGuardada.getPedido().getLineasPedido();
        List<LineaPedidoResponse> lineasPedidoResponse = lineaPedidoServicio.crearLineaPedidoResponse(lineasPedido);
        pedidoResponse.setPedidoId(facturaGuardada.getPedido().getPedidoId());
        pedidoResponse.setLineasPedidoResponse(lineasPedidoResponse);
        facturaResponse.setPedidoResponse(pedidoResponse);

        // --- Datos de la Reserva ---
        facturaResponse.setReservaId(facturaGuardada.getReserva().getReservaId());

        // --- Datos de la Factura ---
        facturaResponse.setFacturaId(facturaGuardada.getFacturaId());
        facturaResponse.setFacturaPrecio(facturaGuardada.getFacturaPrecioTotal());
        facturaResponse.setFechaFactura(facturaGuardada.getFacturaFecha());
        facturaResponse.setFormaPago(facturaGuardada.getFormaPago().name());

        return facturaResponse;
    }
}