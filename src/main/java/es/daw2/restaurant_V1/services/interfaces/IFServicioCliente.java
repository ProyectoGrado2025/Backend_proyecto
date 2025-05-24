package es.daw2.restaurant_V1.services.interfaces;

import java.util.ArrayList;
import java.util.Optional;

import es.daw2.restaurant_V1.models.Cliente;

public interface IFServicioCliente {

    // Métodos a implementar
    public abstract ArrayList<Cliente> getClients();
    public abstract Optional<Cliente> getClientById(Long id);
    public abstract boolean createClient(Cliente client);
    public abstract boolean updateClient(Cliente client, Long id);
    public abstract boolean deleteCliente(Long id);
}
