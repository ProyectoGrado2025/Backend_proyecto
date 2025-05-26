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
    
    @Override
    public CategoriaResponse crearCategoria(CategoriaRequest categoriaRequest) {

        Categoria categoria = new Categoria();
        categoria.setCategoriaNombre(categoriaRequest.getNombreCategoria());
        categoria.setDescripcion(categoriaRequest.getDescripcion());

        List<Plato> platos = new ArrayList<>();
        if (categoriaRequest.getPlatosIds() != null && !categoriaRequest.getPlatosIds().isEmpty()) {
            platos = platoRepositorio.findAllById(categoriaRequest.getPlatosIds());

            // se sincroniza la relación desde Plato a Categoria
            for (Plato p : platos) {
                if (p.getCategorias() == null) {
                    p.setCategorias(new ArrayList<>());
                }
                if (!p.getCategorias().contains(categoria)) {
                    p.getCategorias().add(categoria);
                }
            }
        }
        categoria.setPlatos(platos);

        // se guarda la categoría primero para tener ID si hace falta
        Categoria categoriaGuardada = categoriaRepositorio.save(categoria);

        // se guardan los platos actualizados con la relación
        for (Plato p : platos) {
            platoRepositorio.save(p);
        }

        return composeCategoriaResponse(categoriaGuardada);
    }


    @Override
    public CategoriaResponse actualizarCategoria(Long categoriaId, CategoriaRequest categoriaRequest) {
        Categoria categoriaFromDb = categoriaRepositorio.findById(categoriaId)
                .orElseThrow(() -> new EntityNotFoundException("Categoria no encontrada con ID: " + categoriaId));

        if (categoriaRequest.getNombreCategoria() != null) {
            if (!categoriaFromDb.getCategoriaNombre().equalsIgnoreCase(categoriaRequest.getNombreCategoria())) {
                categoriaFromDb.setCategoriaNombre(categoriaRequest.getNombreCategoria());
            }
        }

        if (categoriaRequest.getDescripcion() != null) {
            categoriaFromDb.setDescripcion(categoriaRequest.getDescripcion());
        }

        if (categoriaRequest.getPlatosIds() != null) {
            // se quita la categoría de los platos antiguos
            for (Plato p : categoriaFromDb.getPlatos()) {
                if (p.getCategorias() != null) {
                    p.getCategorias().remove(categoriaFromDb);
                    platoRepositorio.save(p);
                }
            }

            if (categoriaRequest.getPlatosIds().isEmpty()) {
                categoriaFromDb.getPlatos().clear();
            } else {
                List<Plato> nuevosPlatos = platoRepositorio.findAllById(categoriaRequest.getPlatosIds());
                categoriaFromDb.setPlatos(nuevosPlatos);

                // se añade la categoría en los platos nuevos
                for (Plato p : nuevosPlatos) {
                    if (p.getCategorias() == null) {
                        p.setCategorias(new ArrayList<>());
                    }
                    if (!p.getCategorias().contains(categoriaFromDb)) {
                        p.getCategorias().add(categoriaFromDb);
                    }
                    platoRepositorio.save(p);
                }
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