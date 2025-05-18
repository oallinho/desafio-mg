package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.dto.SectionDTO;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.exceptions.drink.InvalidDrinkTypeException;
import br.com.desafio.mg.springboot.exceptions.section.MaximumSectionsException;
import br.com.desafio.mg.springboot.exceptions.section.SectionNotFoundException;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.model.StockModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import br.com.desafio.mg.springboot.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;
    private final StockService stockService;
    private final DrinkRepository drinkRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository, StockService stockService, DrinkRepository drinkRepository) {
        this.sectionRepository = sectionRepository;
        this.stockService = stockService;
        this.drinkRepository = drinkRepository;
    }

    public SectionDTO createSection(SectionDTO dto) {
        StockModel stock = stockService.findStockOrThrow(dto.getStockId());

        long currentSections = sectionRepository.countByStockId(stock.getId());
        if (currentSections >= stock.getMaximumSections()) {
            throw new MaximumSectionsException("Maximum number of sections reached");
        }

        DrinkType permittedType = dto.getPermittedType();
        if (Objects.isNull(permittedType)) {
            List<String> validTypes = Arrays.stream(DrinkType.values()).map(Enum::name).toList();
            throw new InvalidDrinkTypeException("null", validTypes);
        }

        double capacity = switch (permittedType) {
            case ALCOHOLIC -> stock.getAlcoholicMaximum();
            case NON_ALCOHOLIC -> stock.getNonAlcoholicMaximum();
        };

        SectionModel section = new SectionModel();
        section.setPermittedType(permittedType);
        section.setMaximumCapacity(capacity);
        section.setStock(stock);

        SectionModel saved = sectionRepository.save(section);
        return new SectionDTO(saved);
    }

    public List<SectionDTO> getAllSections() {
        List<SectionModel> section = sectionRepository.findAll();
        return section.stream().map(SectionDTO::new).collect(Collectors.toList());
    }

    public Optional<SectionDTO> getSectionById(Long id) {
        return sectionRepository.findById(id).map(SectionDTO::new);
    }

    public SectionModel saveSection(SectionModel drink) {
        return sectionRepository.save(drink);
    }

    public void deleteSection(Long id) {
        SectionModel section = findSectionOrThrow(id);
        sectionRepository.delete(section);
    }

    public List<SectionDTO> getSectionsByStockId(Long stockId) {
        List<SectionModel> section = sectionRepository.findByStockId(stockId);

        return section.stream().map(SectionDTO::new).collect(Collectors.toList());
    }

    public void saveAll(List<SectionModel> sections) {
        sectionRepository.saveAll(sections);
    }

    public SectionModel findSectionOrThrow(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new SectionNotFoundException(id));
    }

    public List<SectionDTO> getFilteredSections(DrinkType type, Double requiredVolume) {
        Map<Long, Double> sectionVolumeMap = drinkRepository.getCurrentVolumesBySection()
                .stream().collect(Collectors.toMap(entry -> (Long) entry[0], entry -> (Double) entry[1]));

        List<SectionModel> sections = (type == null) ? sectionRepository.findAll() : sectionRepository.findByPermittedType(type);

        return sections.stream().map(section -> {
            double currentVolume = sectionVolumeMap.getOrDefault(section.getId(), 0.0);
            return new AbstractMap.SimpleEntry<>(section, currentVolume);
        }).filter(entry -> {
            SectionModel section = entry.getKey();
            double currentVolume = entry.getValue();
            return currentVolume < section.getMaximumCapacity() && (requiredVolume == null || currentVolume + requiredVolume <= section.getMaximumCapacity());
        }).map(entry -> new SectionDTO(entry.getKey(), entry.getValue())).toList();
    }
}
