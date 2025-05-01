package es.daw2.restaurant_V1.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.repositories.FacturaRepositorio;

@Service
public class ImpServicioFactura implements IFServicioFactura{

    @Autowired
    FacturaRepositorio facturaRepo;
}
