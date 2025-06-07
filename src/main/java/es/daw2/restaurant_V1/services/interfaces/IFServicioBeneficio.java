package es.daw2.restaurant_V1.services.interfaces;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import es.daw2.restaurant_V1.dtos.beneficios.*;
import es.daw2.restaurant_V1.models.Beneficio;

public interface IFServicioBeneficio {

    public abstract BeneficioResponse crearBeneficio (BeneficioRequest beneficioRequest);
    public abstract List<Beneficio> getAllBeneficiosAvilables();
    public Beneficio getBeneficioRandom();
    public Page<BeneficioResponse> getAllBeneficios(Pageable pageable);
    public BeneficioResponse getBeneficioById(Long id);
    public BeneficioResponse disableBeneficio (Long id);
    public BeneficioResponse enableBeneficio (Long id);
    public BeneficioResponse composeBeneficioResponse (Beneficio beneficio);
}
