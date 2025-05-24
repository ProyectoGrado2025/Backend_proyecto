package es.daw2.restaurant_V1.services.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.alergenos.AlergenoRequest;
import es.daw2.restaurant_V1.models.Alergeno;

public interface IFServicioAlergeno {

    public abstract Alergeno crearAlergeno (AlergenoRequest alergenoRequest);

    public abstract Page<Alergeno> getAllAlergenos(Pageable pageable);

    public Alergeno getAlergenoById(Long id);

    public Alergeno updateAlergeno(Long id, AlergenoRequest alergenoRequest);
    
}
