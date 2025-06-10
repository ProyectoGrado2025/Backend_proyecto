package es.daw2.restaurant_V1.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.clientes.ClienteRangoRequest;
import es.daw2.restaurant_V1.dtos.clientes.ClienteRangoResponse;
import es.daw2.restaurant_V1.dtos.clientes.ClienteResponse;

public interface IFServicioCliente {

    public abstract Page<ClienteResponse> getAllClientes (Pageable pageable);
    public abstract ClienteResponse getClienteById(Long id);
    public abstract ClienteRangoResponse consultarRangoInfoById (ClienteRangoRequest clienteRangoRequest, Long id);
}
