package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.dto.DrinkDTO;
import br.com.desafio.mg.springboot.dto.request.DrinkRequest;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.exceptions.drink.DivergentDrinkTypeException;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkNotFoundException;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final SectionService sectionService;

    @Autowired
    public DrinkService(DrinkRepository drinkRepository, SectionService sectionService) {
        this.drinkRepository = drinkRepository;
        this.sectionService = sectionService;
    }

    public List<DrinkModel> getAllDrinks() {
        return drinkRepository.findAll();
    }

    public Optional<DrinkModel> getDrinkById(Long id) {
        return drinkRepository.findById(id);
    }

    public List<DrinkModel> getDrinksBySection(Long sectionId) {

        return drinkRepository.getDrinksBySectionId(sectionId);
    }

    public DrinkModel createDrink(DrinkRequest request) {
        SectionModel section = sectionService.getSectionById(request.getSectionId());

        if (request.getType() != section.getPermittedType()) {
            throw new DivergentDrinkTypeException(section.getPermittedType());
        }

        DrinkModel drink = new DrinkModel();
        drink.setName(request.getName());
        drink.setLiter(request.getLiter());
        drink.setType(request.getType());
        drink.setSection(section);

        return drinkRepository.save(drink);
    }

    public void deleteDrink(Long id) {
        DrinkModel drink = findDrinkOrThrow(id);
        drinkRepository.delete(drink);
    }

    private DrinkModel findDrinkOrThrow(Long id) {
        return drinkRepository.findById(id).orElseThrow(() -> new DrinkNotFoundException(id));
    }

    public Map<DrinkType, Double> getTotalVolumeByType() {
        List<Object[]> results = drinkRepository.getTotalVolumeByType();
        Map<DrinkType, Double> volumeMap = new HashMap<>();

        for (Object[] result : results) {
            DrinkType type = (DrinkType) result[0];
            Double totalVolume = (Double) result[1];
            volumeMap.put(type, totalVolume);
        }

        return volumeMap;
    }



}

