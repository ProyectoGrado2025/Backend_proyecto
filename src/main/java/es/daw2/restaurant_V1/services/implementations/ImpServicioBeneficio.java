package es.daw2.restaurant_V1.services.implementations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.beneficios.BeneficioRequest;
import es.daw2.restaurant_V1.dtos.beneficios.BeneficioResponse;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.models.Beneficio;
import es.daw2.restaurant_V1.models.Beneficio.BeneficioStatus;
import es.daw2.restaurant_V1.repositories.BeneficioRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioBeneficio;
import io.netty.util.internal.ThreadLocalRandom;

@Service
public class ImpServicioBeneficio implements IFServicioBeneficio{

    @Autowired
    BeneficioRepositorio beneficioRepositorio;

    @Override
    public Page<BeneficioResponse> getAllBeneficios(Pageable pageable){
        return beneficioRepositorio.findAll(pageable)
                .map(this::composeBeneficioResponse);
    }

    @Override
    public BeneficioResponse getBeneficioById(Long id){
        Beneficio beneficio = beneficioRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Beneficio no encontrado con ID: "+id));

        return composeBeneficioResponse(beneficio);
    }

    @Override
    public List<Beneficio> getAllBeneficiosAvilables() {
        return beneficioRepositorio.findAllByStatus(BeneficioStatus.HABILITADO);
    }

    @Override
    public Beneficio getBeneficioRandom() {
        List<Beneficio> beneficios = getAllBeneficiosAvilables();
        if (beneficios.isEmpty()) {
            throw new EntityNotFoundException("No hay beneficios disponibles.");
        }

        int randomIndex = ThreadLocalRandom.current().nextInt(beneficios.size());
        return beneficios.get(randomIndex);
    }

    @Override
    public BeneficioResponse disableBeneficio (Long id){
        Beneficio beneficio = beneficioRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Beneficio no encontrado con ID:"+id));
        if(beneficio.getStatus() == BeneficioStatus.DESHABILITADO){
            throw new IllegalArgumentException("BENEFICIO '"+beneficio.getCodigo()+"' YA DESHABILITADO");
        }

        beneficio.setStatus(BeneficioStatus.DESHABILITADO);
        
        Beneficio benefioActualizado = beneficioRepositorio.save(beneficio);
        return composeBeneficioResponse(benefioActualizado);
    }

    @Override
    public BeneficioResponse enableBeneficio (Long id){
        Beneficio beneficio = beneficioRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Beneficio no encontrado con ID:"+id));

        if(beneficio.getStatus() == BeneficioStatus.HABILITADO){
            throw new IllegalArgumentException("BENEFICIO '"+beneficio.getCodigo()+"' YA HABILITADO");
        }

        beneficio.setStatus(BeneficioStatus.HABILITADO);

        Beneficio benefioActualizado = beneficioRepositorio.save(beneficio);
        return composeBeneficioResponse(benefioActualizado);
    }

    @Override
    public BeneficioResponse crearBeneficio(BeneficioRequest beneficioRequest) {
        Beneficio beneficio = new Beneficio();
        beneficio.setCodigo(beneficioRequest.getCodBeneficio());
        beneficio.setDescripcion(beneficioRequest.getDecripBeneficio());
        beneficio.setStatus(BeneficioStatus.HABILITADO);

        Beneficio beneficioGuardado = beneficioRepositorio.save(beneficio);
        BeneficioResponse beneficioResponse = composeBeneficioResponse(beneficioGuardado);

        return beneficioResponse;
    }

    @Override
    public BeneficioResponse composeBeneficioResponse (Beneficio beneficio){
        BeneficioResponse beneficioResponse = new BeneficioResponse();
        beneficioResponse.setIdBeneficio(beneficio.getId());
        beneficioResponse.setCodBeneficio(beneficio.getCodigo());
        beneficioResponse.setDecripBeneficio(beneficio.getDescripcion());
        beneficioResponse.setStatus(beneficio.getStatus().toString());

        return beneficioResponse;
    }
}