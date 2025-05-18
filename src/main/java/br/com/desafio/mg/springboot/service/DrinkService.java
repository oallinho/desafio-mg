package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.dto.DrinkDTO;
import br.com.desafio.mg.springboot.enums.DrinkStatus;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.enums.TransactionType;
import br.com.desafio.mg.springboot.exceptions.drink.DivergentDrinkTypeException;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkAlreadySoldException;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkNotFoundException;
import br.com.desafio.mg.springboot.exceptions.section.SectionNotFoundException;
import br.com.desafio.mg.springboot.listener.TransactionEvent;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import br.com.desafio.mg.springboot.repository.SectionRepository;
import br.com.desafio.mg.springboot.validator.SectionValidator;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class DrinkService {

    private final DrinkRepository drinkRepository;
    private final SectionService sectionService;
    private final SectionRepository sectionRepository;
    private final SectionValidator sectionValidator;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public DrinkService(DrinkRepository drinkRepository, SectionService sectionService, SectionRepository sectionRepository, SectionValidator sectionValidator, ApplicationEventPublisher eventPublisher) {
        this.drinkRepository = drinkRepository;
        this.sectionService = sectionService;
        this.sectionRepository = sectionRepository;
        this.sectionValidator = sectionValidator;
        this.eventPublisher = eventPublisher;
    }

    public List<DrinkDTO> getAllDrinks() {
        return drinkRepository.findAll()
                .stream()
                .map(DrinkDTO::new)
                .collect(Collectors.toList());
    }

    public Optional<DrinkDTO> getDrinkById(Long id) {
        return drinkRepository.findById(id)
                .map(DrinkDTO::new);
    }

    public List<DrinkDTO> getDrinksBySection(Long sectionId) {
        List<DrinkModel> drinks = drinkRepository.getDrinksBySectionId(sectionId);
        return drinks.stream()
                .map(DrinkDTO::new)
                .toList();
    }

    public DrinkDTO createDrink(DrinkDTO dto, String responsible) {
        SectionModel section = sectionService.findSectionOrThrow(dto.getSectionId());

        sectionValidator.validateDrinkType(section, dto.getType());
        sectionValidator.validateSectionCapacity(section, dto.getVolume());

        DrinkModel drink = dto.toModel(section);
        drink.setName(dto.getName());
        drink.setVolume(dto.getVolume());
        drink.setType(dto.getType());
        drink.setSection(section);
        drink.setStatus(DrinkStatus.ACTIVE);

        drink = drinkRepository.save(drink);

        eventPublisher.publishEvent(new TransactionEvent(this, drink.getId(),
                dto.getSectionId(), responsible, TransactionType.ENTRY, String.format("New drink '%s' registered in Section %d", drink.getName(), drink.getSection().getId())));

        return new DrinkDTO(drink);
    }

    public void updateDrink(Long drinkId, Long newSectionId) {
        DrinkModel drink = findDrinkOrThrow(drinkId);

        SectionModel newSection = sectionRepository.findById(newSectionId).orElseThrow(() -> new SectionNotFoundException(newSectionId));

        if (newSection.getPermittedType() != drink.getType()) {
            throw new DivergentDrinkTypeException(drink.getType());
        }

        sectionValidator.validateDrinkType(newSection, newSection.getPermittedType());
        sectionValidator.validateSectionCapacity(newSection, drink.getVolume());

        drink.setSection(newSection);
        drink.setUpdatedAt(LocalDateTime.now());

        drinkRepository.save(drink);
    }

    public void sellDrink(Long drinkId, String responsible){
        DrinkModel drink = findDrinkOrThrow(drinkId);

        if (drink.getStatus() == DrinkStatus.SOLD)
            throw new DrinkAlreadySoldException(drinkId);

        eventPublisher.publishEvent(new TransactionEvent(this,
                drink.getId(),
                drink.getSection().getId(),
                responsible,
                TransactionType.EXIT, String.format("Drink '%s' sold and removed from Section %d", drink.getName(), drink.getSection().getId())));

        drink.setSection(null);
        drink.setUpdatedAt(LocalDateTime.now());
        drink.setStatus(DrinkStatus.SOLD);
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




