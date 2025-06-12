package es.daw2.restaurant_V1.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.clientes.ClienteRangoRequest;
import es.daw2.restaurant_V1.dtos.clientes.ClienteRangoResponse;
import es.daw2.restaurant_V1.dtos.clientes.ClienteResponse;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.models.Cliente;
import es.daw2.restaurant_V1.repositories.ClienteRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioCliente;

@Service
public class ImpServicioCliente implements IFServicioCliente {

    @Autowired
    ClienteRepositorio clientRepository;

    @Override
    public Page<ClienteResponse> getAllClientes(Pageable pageable) {
        return clientRepository.findAll(pageable)
                .map(this::composeClienteResponse);
    }

    @Override
    public ClienteResponse getClienteById(Long id) {
        Cliente cliente = clientRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Cliente no encontrado con ID: " + id));
        return composeClienteResponse(cliente);
    }

    @Override
    public ClienteRangoResponse consultarRangoInfoById (String email, Long id){
        Cliente cliente = clientRepository.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Cliente no encontrado con ID: " + id));
        if(!email.equals(cliente.getEmail())){
            throw new IllegalArgumentException("Se ha encontrado el cliente, pero los EMAILS NO COINCIDEN");
        }

        return composeClienteRangoInfo(cliente);
    }

    private ClienteRangoResponse composeClienteRangoInfo(Cliente cliente){
        ClienteRangoResponse clienteRangoResponse = new ClienteRangoResponse();
        clienteRangoResponse.setNombreCliente(cliente.getClienteNombre());
        clienteRangoResponse.setEmailCliente(cliente.getEmail());
        clienteRangoResponse.setRangoCliente(cliente.getRango().getNombreRango());
        clienteRangoResponse.setPntosCliente(cliente.getPuntosFidelizacion());
        clienteRangoResponse.setDescuento(cliente.getRango().getDescuento().longValue());

        return clienteRangoResponse;
    }

    private ClienteResponse composeClienteResponse (Cliente cliente){
        ClienteResponse clienteResponse = new ClienteResponse();
        clienteResponse.setClienteId(cliente.getId());
        clienteResponse.setEmailCliente(cliente.getEmail());
        clienteResponse.setNombreCliente(cliente.getClienteNombre());
        clienteResponse.setNumeroCliente(cliente.getClienteTlfn());
        clienteResponse.setRangoCliente(cliente.getRango().getNombreRango());
        clienteResponse.setClientePuntos(cliente.getPuntosFidelizacion());

        return clienteResponse;
    }
}
