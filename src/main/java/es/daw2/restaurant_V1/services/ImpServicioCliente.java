package es.daw2.restaurant_V1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.repositories.ClienteRepositorio;

@Service
public class ImpServicioCliente implements IFServicioCliente {

    @Autowired
    ClienteRepositorio clienteRepo;
}
