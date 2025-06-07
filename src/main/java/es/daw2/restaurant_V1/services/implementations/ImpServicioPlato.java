package es.daw2.restaurant_V1.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.platos.PlatoRequest;
import es.daw2.restaurant_V1.dtos.platos.PlatoResponse;
import es.daw2.restaurant_V1.models.Alergeno;
import es.daw2.restaurant_V1.models.Categoria;
import es.daw2.restaurant_V1.models.Plato;
import es.daw2.restaurant_V1.repositories.AlergenoRepositorio;
import es.daw2.restaurant_V1.repositories.CategoriaRepositorio;
import es.daw2.restaurant_V1.repositories.PlatoRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioPlato;
import jakarta.persistence.EntityNotFoundException;

@Service
public class ImpServicioPlato implements IFServicioPlato{

    @Autowired
    PlatoRepositorio platoRepositorio;
    @Autowired
    AlergenoRepositorio alergenoRepositorio;
    @Autowired
    CategoriaRepositorio categoriaRepositorio;

    @Override
    public Page<PlatoResponse> getAllPlatos(Pageable pageable){
        return platoRepositorio.findAll(pageable)
                .map(this::composePlatoResponse);
    }

    @Override
    public PlatoResponse getPlatoById(Long id){
        Plato plato= platoRepositorio.findById(id)
                .orElseThrow(()-> new EntityNotFoundException("Plato no encontrado con ID: " + id));
        return composePlatoResponse(plato);
    }

    @Override
    public PlatoResponse crearPlato(PlatoRequest platoRequest) {
        Plato plato = new Plato();
        plato.setPlatoNombre(platoRequest.getPlatoNombre());
        plato.setPlatoPrecio(platoRequest.getPlatoPrecio());
        plato.setPlatoDescripcion(platoRequest.getPlatoDescripcion());

        List<Alergeno> alergenos = new ArrayList<>();
        if (platoRequest.getAlergenos() != null && !platoRequest.getAlergenos().isEmpty()) {
            alergenos = alergenoRepositorio.findAllById(platoRequest.getAlergenos());
            if (alergenos.size() != platoRequest.getAlergenos().size()) {
                throw new EntityNotFoundException("Uno o más alérgenos no existen");
            }
        }
        plato.setAlergenos(alergenos);

        List<Categoria> categorias = new ArrayList<>();
        if (platoRequest.getCategorias() != null && !platoRequest.getCategorias().isEmpty()) {
            categorias = categoriaRepositorio.findAllById(platoRequest.getCategorias());
            if (categorias.size() != platoRequest.getCategorias().size()) {
                throw new EntityNotFoundException("Una o más categorías no existen");
            }
        }
        plato.setCategorias(categorias);

        Plato platoGuardado = platoRepositorio.save(plato);

        return composePlatoResponse(platoGuardado);
    }

    @Override
    public PlatoResponse actualizarPlato (Long id, PlatoRequest platoRequest){
        Plato platoFromDb = platoRepositorio.findById(id)
                .orElseThrow( () -> new EntityNotFoundException("Plato no encontrato con el ID: " + id) );
        if(platoRequest.getPlatoNombre() != null){
            platoFromDb.setPlatoNombre(platoRequest.getPlatoNombre());
        }

        if(platoRequest.getPlatoDescripcion() != null){
            platoFromDb.setPlatoDescripcion(platoRequest.getPlatoDescripcion());
        }

        if(platoRequest.getPlatoPrecio() != null){
            platoFromDb.setPlatoPrecio(platoRequest.getPlatoPrecio());
        }

        List<Alergeno> alergenos = new ArrayList<>();
        if(platoRequest.getAlergenos() != null && !platoRequest.getAlergenos().isEmpty()){
            alergenos = alergenoRepositorio.findAllById(platoRequest.getAlergenos());
            if (alergenos.size() != platoRequest.getAlergenos().size()) {
                throw new EntityNotFoundException("Uno o más alérgenos no existen");
            }
        }
        platoFromDb.setAlergenos(alergenos);

        List<Categoria> categorias = new ArrayList<>();
        if (platoRequest.getCategorias() != null && !platoRequest.getCategorias().isEmpty()) {
            categorias = categoriaRepositorio.findAllById(platoRequest.getCategorias());
            if (categorias.size() != platoRequest.getCategorias().size()) {
                throw new EntityNotFoundException("Una o más categorías no existen");
            }
        }
        platoFromDb.setCategorias(categorias);

        Plato platoActualizado = platoRepositorio.save(platoFromDb);
        return composePlatoResponse(platoActualizado);
    }

    private PlatoResponse composePlatoResponse(Plato plato) {
        PlatoResponse platoResponse = new PlatoResponse();
        platoResponse.setPlatoId(plato.getPlatoId());
        platoResponse.setPlatoNombre(plato.getPlatoNombre());
        platoResponse.setPlatoDescripcion(plato.getPlatoDescripcion());
        platoResponse.setPlatoPrecio(plato.getPlatoPrecio());

        List<String> nombreAlergenos = plato.getAlergenos().stream()
                .map(Alergeno::getNombreAlergeno)
                .collect(Collectors.toList());
        platoResponse.setAlergenos(nombreAlergenos);

        List<String> nombreCategorias = plato.getCategorias().stream()
                .map(Categoria::getCategoriaNombre)
                .collect(Collectors.toList());
        platoResponse.setCategorias(nombreCategorias);

        return platoResponse;
    }
}
