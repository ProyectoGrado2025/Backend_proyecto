package es.daw2.restaurant_V1.services;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.models.PlatoCategoria;
import es.daw2.restaurant_V1.repositories.PlatoCategoriaRepositorio;

@Service
public class ImpServicioPlatoCategoria implements IFServicioPlatoCategoria{

    @Autowired
    PlatoCategoriaRepositorio categoryRepository;

    @Override
    public ArrayList<PlatoCategoria> getCategories() {
        return (ArrayList<PlatoCategoria>) categoryRepository.findAll();
    }

    @Override
    public Optional<PlatoCategoria> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public boolean addCategory(PlatoCategoria category) {
        if(category != null){
            categoryRepository.save(category);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateCategory(PlatoCategoria category, Long id) {
        Optional<PlatoCategoria> categoryContainer = categoryRepository.findById(id);

        if(categoryContainer.isPresent()){
            PlatoCategoria existingCategory = categoryContainer.get();
            existingCategory.setCategoria_nombre(category.getCategoria_nombre());

            return true;
        }
        return false;
    }

    @Override
    public boolean deleteCategory(Long id) {
        Optional<PlatoCategoria> categoryContainer = categoryRepository.findById(id);
        
        if(categoryContainer.isPresent()){
            categoryRepository.deleteById(id);

            return true;
        }
        return false;
    }
}
