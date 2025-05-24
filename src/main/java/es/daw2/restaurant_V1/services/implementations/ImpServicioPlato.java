package es.daw2.restaurant_V1.services.implementations;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.daw2.restaurant_V1.models.Plato;
import es.daw2.restaurant_V1.repositories.PlatoRepositorio;
import es.daw2.restaurant_V1.services.interfaces.IFServicioPlato;

@Service
public class ImpServicioPlato implements IFServicioPlato{

    @Autowired
    PlatoRepositorio dishRepository;

    @Override
    public ArrayList<Plato> getDishes() {
        return (ArrayList<Plato>) dishRepository.findAll();
    }

    @Override
    public Optional<Plato> getDishById(Long id) {
        return dishRepository.findById(id);
    }

    @Override
    public boolean addDish(Plato dish) {
        if(dish != null){
            dishRepository.save(dish);
            return true;
        }
        return false;
    }

    @Override
    public boolean updateDish(Plato dish, Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateDish'");
    }

    @Override
    public boolean deleteDish(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'deleteDish'");
    }
}
