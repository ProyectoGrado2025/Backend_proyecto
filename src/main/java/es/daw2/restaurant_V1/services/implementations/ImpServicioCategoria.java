package es.daw2.restaurant_V1.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.categoria.CategoriaRequest;
import es.daw2.restaurant_V1.dtos.categoria.CategoriaResponse;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.models.Categoria;
import es.daw2.restaurant_V1.models.Plato;
import es.daw2.restaurant_V1.repositories.CategoriaRepositorio;
import es.daw2.restaurant_V1.repositories.PlatoRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioCategoria;

@Service
public class ImpServicioCategoria implements IFServicioCategoria{

    @Autowired
    CategoriaRepositorio categoriaRepositorio;
    @Autowired
    PlatoRepositorio platoRepositorio;

    @Override
    public Page<CategoriaResponse> getAllCategorias (Pageable pageable){
        return categoriaRepositorio.findAll(pageable)
                .map(this::composeCategoriaResponse);
    }

    @Override
    public CategoriaResponse getCategoriaById(Long id) {
        Categoria categoriaFromDb = categoriaRepositorio.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Categoría no encontrada con ID: " + id));

        return composeCategoriaResponse(categoriaFromDb);
    }
    /**
     * PUEDES CREAR UNA CATEGORÍA SOLA, ES DECIR, SIN PLATOS
     * PUEDES CREAR UNA CATEGORÍA EN LA QUE SE INCLUYEN TODOS LOS PLATOS INDICANDO LOS IDs
     */
    @Override
    public CategoriaResponse crearCategoria (CategoriaRequest categoriaRequest){

        Categoria categoria = new Categoria();
        categoria.setCategoriaNombre(categoriaRequest.getNombreCategoria());
        categoria.setDescripcion(categoriaRequest.getDescripcion());

        List<Plato> platos = new ArrayList<>();
        if(categoriaRequest.getPlatosIds() != null && !categoriaRequest.getPlatosIds().isEmpty()){
            platos = platoRepositorio.findAllById(categoriaRequest.getPlatosIds());
        }

        categoria.setPlatos(platos);

        return composeCategoriaResponse(categoria);
    }

    /**
     * PARA ACTUALIZAR UNA CATEGORÍA, COMO MÁXIMO PUEDES ENVIAR:
     *  - NOMBRE
     *  - DESCRIPCIÓN
     *  - LISTA NUEVA DE PLATOS
     */
    @Override 
    public CategoriaResponse actualizarCategoria(Long categoriaId, CategoriaRequest categoriaRequest){
        Categoria categoriaFromDb = categoriaRepositorio.findById(categoriaId)
                .orElseThrow(()->new EntityNotFoundException("Categoria no encontrada con ID: " + categoriaId));

        if(categoriaRequest.getNombreCategoria() != null){
            if(!categoriaFromDb.getCategoriaNombre().equalsIgnoreCase(categoriaRequest.getNombreCategoria())){
                categoriaFromDb.setCategoriaNombre(categoriaRequest.getNombreCategoria());
            }
        }

        if(categoriaRequest.getDescripcion() != null){
            categoriaFromDb.setDescripcion(categoriaRequest.getDescripcion());
        }

        if(categoriaRequest.getPlatosIds() != null){
            if(categoriaRequest.getPlatosIds().isEmpty()){
                categoriaFromDb.getPlatos().clear();
            } else {
                List<Plato> nuevosPlatos = platoRepositorio.findAllById(categoriaRequest.getPlatosIds());
                categoriaFromDb.setPlatos(nuevosPlatos);
            }
        }

        Categoria categoriaActualizada = categoriaRepositorio.save(categoriaFromDb);

        return composeCategoriaResponse(categoriaActualizada);
    }

    private CategoriaResponse composeCategoriaResponse(Categoria categoria) {
        CategoriaResponse categoriaResponse = new CategoriaResponse();
        categoriaResponse.setCategoriaId(categoria.getCategoriaId());
        categoriaResponse.setCategoriaNombre(categoria.getCategoriaNombre());
        categoriaResponse.setCategoriaDescrip(categoria.getDescripcion());

        if(categoria.getPlatos() != null){
            List<String> platosNombre = categoria.getPlatos().stream()
                .map(Plato::getPlatoNombre)
                .collect(Collectors.toList());
            
            categoriaResponse.setPlatos(platosNombre);
        }
        return categoriaResponse;
    }

}