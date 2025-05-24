package es.daw2.restaurant_V1.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.alergenos.AlergenoRequest;
import es.daw2.restaurant_V1.exceptions.custom.DuplicateResourceException;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.models.Alergeno;
import es.daw2.restaurant_V1.repositories.AlergenoRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioAlergeno;

@Service
public class ImpServicioAlergeno implements IFServicioAlergeno{

    @Autowired
    AlergenoRepositorio alergenoRepositorio;

    @Override
    public Page<Alergeno> getAllAlergenos(Pageable pageable) {
        return alergenoRepositorio.findAll(pageable);
    }

    @Override
    public Alergeno getAlergenoById(Long id) {
        return alergenoRepositorio.findById(id)
                .orElseThrow(()->new EntityNotFoundException("Entidad no encontrada con ID: " + id));
    }

    @Override
    public Alergeno crearAlergeno(AlergenoRequest alergenoRequest) {

        // nombre obligatorio
        if (alergenoRequest.getNombreAlergeno() == null || alergenoRequest.getNombreAlergeno().isBlank()) {
            throw new IllegalArgumentException("El nombre del alérgeno no puede estar vacío.");
        }

        // validación de duplicado
        if (alergenoRepositorio.existsByNombreAlergeno(alergenoRequest.getNombreAlergeno())) {
            throw new DuplicateResourceException("Ya existe un alérgeno con ese nombre.");
        }

        Alergeno alergeno = new Alergeno();
        alergeno.setNombreAlergeno(alergenoRequest.getNombreAlergeno());
        alergeno.setDescripcionAlergeno(alergenoRequest.getDescripcionAlergeno());

        return alergenoRepositorio.save(alergeno);
    }

    @Override
    public Alergeno updateAlergeno(Long id, AlergenoRequest alergenoRequest) {
        Alergeno alergenoFromDb = alergenoRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Alérgeno no contrado con ID:" + id));
        
        alergenoFromDb.setNombreAlergeno(alergenoRequest.getNombreAlergeno());
        alergenoFromDb.setDescripcionAlergeno(alergenoRequest.getDescripcionAlergeno());

        return alergenoRepositorio.save(alergenoFromDb);
    }
}
