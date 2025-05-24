package es.daw2.restaurant_V1.services.implementations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.dtos.categoria.CategoriaRequest;
import es.daw2.restaurant_V1.dtos.categoria.CategoriaResponse;
import es.daw2.restaurant_V1.exceptions.custom.EntityNotFoundException;
import es.daw2.restaurant_V1.models.Categoria;
import es.daw2.restaurant_V1.models.Plato;
import es.daw2.restaurant_V1.repositories.PlatoCategoriaRepositorio;
import es.daw2.restaurant_V1.repositories.PlatoRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioCategoria;

@Service
public class ImpServicioCategoria implements IFServicioCategoria{

    @Autowired
    PlatoCategoriaRepositorio categoriaRepositorio;
    @Autowired
    PlatoRepositorio platoRepositorio;


    @Override
    public ArrayList<Categoria> getCategories() {
        return (ArrayList<Categoria>) categoriaRepositorio.findAll();
    }

    @Override
    public Optional<Categoria> getCategoryById(Long id) {
        return categoriaRepositorio.findById(id);
    }

    @Override
    public boolean addCategory(Categoria category) {
        if(category != null){
            categoriaRepositorio.save(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCategory(Categoria category, Long id) {
        Optional<Categoria> categoryContainer = categoriaRepositorio.findById(id);

        if(categoryContainer.isPresent()){
            Categoria existingCategory = categoryContainer.get();
            existingCategory.setCategoriaNombre(category.getCategoriaNombre());

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCategory(Long id) {
        Optional<Categoria> categoryContainer = categoriaRepositorio.findById(id);
        
        if(categoryContainer.isPresent()){
            categoriaRepositorio.deleteById(id);

            return true;
        }
        return false;
    }

    //Nuevas IMPLEMENTACIONES
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

        return createCategoriaResponse(categoria);
    }

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

        return createCategoriaResponse(categoriaActualizada);
    }

    private CategoriaResponse createCategoriaResponse(Categoria categoria) {
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