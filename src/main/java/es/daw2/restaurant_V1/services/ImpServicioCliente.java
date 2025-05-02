package es.daw2.restaurant_V1.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.models.Cliente;
import es.daw2.restaurant_V1.repositories.ClienteRepositorio;

@Service
public class ImpServicioCliente implements IFServicioCliente {

    @Autowired
    ClienteRepositorio clientRepository;

    @Override
    public ArrayList<Cliente> getClients() {
        return (ArrayList<Cliente>) clientRepository.findAll();
    }

    @Override
    public Optional<Cliente> getClientById(Long id) {
        return clientRepository.findById(id);
    }

    @Override
    public boolean createClient(Cliente client) {
        if(client != null){
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateClient(Cliente client, Long id) {

        // Se buca al cliente a actualizar por su ID
        Optional<Cliente> clientContainer = clientRepository.findById(id);

        // Si en la clase contenedora hay un objeto de tipo "Cliente"
        if(clientContainer.isPresent()){

            // Se accede a la clase contenida y se actualizan los datos
            Cliente existingClient = clientContainer.get();
            existingClient.setCliente_email(client.getCliente_email());
            existingClient.setCliente_nombre(client.getCliente_nombre());
            existingClient.setCliente_tlfn(client.getCliente_tlfn());

            clientRepository.save(existingClient);

            return true;
        }

        return false;
    }

    @Override
    public boolean deleteCliente(Long id) {
        
        Optional<Cliente> clientContainer = clientRepository.findById(id);

        if(clientContainer.isPresent()){
            clientRepository.deleteById(id);

            return true;
        }

        return false;
    }
}
