package es.daw2.restaurant_V1.dtos.mesas;

import java.io.Serializable;

import es.daw2.restaurant_V1.models.Mesa.MesaStatus;
import lombok.Data;

@Data
public class MesaUpdateRequest implements Serializable{
    private Integer nuevoMesaNumero;
    private Integer mesaCapacidad;
    private MesaStatus mesaStatus;
}