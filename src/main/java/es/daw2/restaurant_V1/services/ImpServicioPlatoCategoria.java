package es.daw2.restaurant_V1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.repositories.PlatoCategoriaRepositorio;

@Service
public class ImpServicioPlatoCategoria implements IFServicioPlatoCategoria{

    @Autowired
    PlatoCategoriaRepositorio platoCategoriaRepo;
}
