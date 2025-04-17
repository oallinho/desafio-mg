package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;

    @Autowired
    public DrinkService(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public List<DrinkModel> getAllDrinks() {
        return drinkRepository.findAll();
    }

    public Optional<DrinkModel> getDrinkById(Long id) {
        return drinkRepository.findById(id);
    }

    public DrinkModel saveDrink(DrinkModel drink) {
        return drinkRepository.save(drink);
    }

    public void deleteDrink(Long id) {
        drinkRepository.deleteById(id);
    }
}

