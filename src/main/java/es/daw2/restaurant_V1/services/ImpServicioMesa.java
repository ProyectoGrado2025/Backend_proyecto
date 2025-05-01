package es.daw2.restaurant_V1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.repositories.MesaRepositorio;

@Service
public class ImpServicioMesa implements IFServicioMesa{

    @Autowired
    MesaRepositorio mesaRepo;
}
