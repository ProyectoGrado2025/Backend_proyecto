package es.daw2.restaurant_V1.dtos.facturas;

import java.io.Serializable;

import es.daw2.restaurant_V1.models.Factura.FormaPago;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data   
public class FacturaRequest implements Serializable{

    @NotNull
    private Long pedidoId;

    @NotNull
    private FormaPago formaPago;
}
