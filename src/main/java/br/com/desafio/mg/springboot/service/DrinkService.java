package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.dto.request.DrinkRequest;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.exceptions.drink.DivergentDrinkTypeException;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkNotFoundException;
import br.com.desafio.mg.springboot.exceptions.section.SectionCapacityExceededException;
import br.com.desafio.mg.springboot.exceptions.section.SectionNotFoundException;
import br.com.desafio.mg.springboot.listener.DrinkEvent;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import br.com.desafio.mg.springboot.repository.SectionRepository;
import br.com.desafio.mg.springboot.validator.SectionCapacityValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final SectionService sectionService;
    private final SectionRepository sectionRepository;
    private final SectionCapacityValidator sectionCapacityValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public DrinkService(DrinkRepository drinkRepository, SectionService sectionService, SectionRepository sectionRepository, SectionCapacityValidator sectionCapacityValidator, ApplicationEventPublisher eventPublisher) {
        this.drinkRepository = drinkRepository;
        this.sectionService = sectionService;
        this.sectionRepository = sectionRepository;
        this.sectionCapacityValidator = sectionCapacityValidator;

        this.eventPublisher = eventPublisher;
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
        drink.setVolume(request.getVolume());
        drink.setType(request.getType());
        drink.setSection(section);

        drink = drinkRepository.save(drink);

        eventPublisher.publishEvent(new DrinkEvent(this, drink.getId(), section.getId()));

        return drink;
    }

    @Transactional
    public void updateDrink(Long drinkId, Long newSectionId) {
        DrinkModel drink = drinkRepository.findById(drinkId)
                .orElseThrow(() -> new DrinkNotFoundException(drinkId));

        SectionModel newSection = sectionRepository.findById(newSectionId)
                .orElseThrow(() -> new SectionNotFoundException(newSectionId));

        if (newSection.getPermittedType() != drink.getType()) {
            throw new DivergentDrinkTypeException(drink.getType());
        }

        if (!sectionCapacityValidator.hasCapacity(newSection, drink.getVolume())) {
            throw new SectionCapacityExceededException(newSection.getId());
        }

        drink.setSection(newSection);
        drink.setUpdatedAt(LocalDateTime.now());

        drinkRepository.save(drink);
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




