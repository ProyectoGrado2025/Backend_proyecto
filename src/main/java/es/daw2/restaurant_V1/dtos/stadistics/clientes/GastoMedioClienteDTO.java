package es.daw2.restaurant_V1.dtos.stadistics.clientes;

import java.io.Serializable;
import java.math.BigDecimal;

import lombok.Data;

@Data
public class GastoMedioClienteDTO implements Serializable{
    
    private Long clienteId;
    private BigDecimal gastoMedio;

    public GastoMedioClienteDTO(Long clienteId, Double gastoMedio) {
        this.clienteId = clienteId;
        this.gastoMedio = gastoMedio != null ? BigDecimal.valueOf(gastoMedio) : null;
    }
}