package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.dto.SectionDTO;
import br.com.desafio.mg.springboot.dto.request.SectionRequest;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.exceptions.drink.InvalidDrinkTypeException;
import br.com.desafio.mg.springboot.exceptions.section.MaximumSectionsException;
import br.com.desafio.mg.springboot.exceptions.section.SectionNotFoundException;
import br.com.desafio.mg.springboot.exceptions.stock.StockNotFoundException;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.model.StockModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import br.com.desafio.mg.springboot.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

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

    public SectionModel createSection(SectionRequest request) {
        StockModel stock = stockService.getStockById(request.getStockId()).orElseThrow(() -> new StockNotFoundException(request.getStockId()));

        long currentSections = sectionRepository.countByStockId(stock.getId());
        if (currentSections >= stock.getMaximumSections()) {
            throw new MaximumSectionsException("Maximum number of sections reached");
        }

        DrinkType permittedType = request.getPermittedType();
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

        return sectionRepository.save(section);
    }

    public List<SectionModel> getAllSections() {
        return sectionRepository.findAll();
    }

    public SectionModel getSectionById(Long id) {
        return findSectionOrThrow(id);
    }

    public SectionModel saveSection(SectionModel drink) {
        return sectionRepository.save(drink);
    }

    public void deleteSection(Long id) {
        SectionModel section = findSectionOrThrow(id);
        sectionRepository.delete(section);
    }

    public List<SectionModel> getSectionsByStockId(Long stockId) {
        return sectionRepository.findByStockId(stockId);
    }

    public void saveAll(List<SectionModel> sections) {
        sectionRepository.saveAll(sections);
    }

    private SectionModel findSectionOrThrow(Long id) {
        return sectionRepository.findById(id).orElseThrow(() -> new SectionNotFoundException(id));
    }

    public List<SectionDTO> getAvailableSections() {
        return getFilteredSections(null, null);
    }

    public List<SectionDTO> getAvailableSectionsByDrinkType(DrinkType type) {
        return getFilteredSections(type, null);
    }

    public List<SectionDTO> getAvailableSectionsByTypeAndVolume(DrinkType type, double requiredVolume) {
        return getFilteredSections(type, requiredVolume);
    }

    public List<SectionDTO> getFilteredSections(DrinkType type, Double requiredVolume) {
        Map<Long, Double> sectionVolumeMap = buildCurrentVolumeMap();

        List<SectionModel> sections = (type == null)
                ? sectionRepository.findAll()
                : sectionRepository.findByPermittedType(type);

        return sections.stream()
                .filter(section -> {
                    double currentVolume = sectionVolumeMap.getOrDefault(section.getId(), 0.0);
                    boolean hasSpace = currentVolume < section.getMaximumCapacity();
                    boolean fitsVolume = requiredVolume == null ||
                            (section.getMaximumCapacity() - currentVolume) >= requiredVolume;
                    return hasSpace && fitsVolume;
                })
                .map(section -> {
                    double currentVolume = sectionVolumeMap.getOrDefault(section.getId(), 0.0);
                    return new SectionDTO(section, currentVolume);
                })
                .toList();
    }

    private Map<Long, Double> buildCurrentVolumeMap() {
        List<Object[]> volumeData = drinkRepository.getCurrentVolumesBySection();
        Map<Long, Double> sectionVolumeMap = new HashMap<>();

        for (Object[] entry : volumeData) {
            sectionVolumeMap.put((Long) entry[0], (Double) entry[1]);
        }

        return sectionVolumeMap;
    }
}
