package es.daw2.restaurant_V1.dtos.facturas;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

import es.daw2.restaurant_V1.dtos.clientes.ClienteResponse;
import es.daw2.restaurant_V1.dtos.pedidos.PedidoResponse;
import lombok.Data;

@Data
public class FacturaResponse implements Serializable {

    private Long facturaId;
    private LocalDateTime fechaFactura;
    private BigDecimal facturaPrecio;
    private String formaPago;

    private PedidoResponse pedidoResponse;
    private ClienteResponse clienteResponse;

    private Long reservaId;
    private String beneficioCodigo;

    private BigDecimal descuentoAplicado;
    private Long puntosGenerados;
    private String nombreRangoAplicado;
}