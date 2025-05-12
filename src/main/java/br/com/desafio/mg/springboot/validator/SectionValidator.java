package br.com.desafio.mg.springboot.validator;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.exceptions.drink.DivergentDrinkTypeException;
import br.com.desafio.mg.springboot.exceptions.section.SectionCapacityExceededException;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SectionValidator {
    private final DrinkRepository drinkRepository;

    public SectionValidator(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public void validateDrinkType(SectionModel section, DrinkType type) {
        if (type != section.getPermittedType()) {
            throw new DivergentDrinkTypeException(section.getPermittedType());
        }
    }

    public void validateSectionCapacity(SectionModel section, double requiredVolume) {
        Map<Long, Double> sectionVolumeMap = buildCurrentVolumeMap();
        double currentVolume = sectionVolumeMap.getOrDefault(section.getId(), 0.0);
        if (currentVolume + requiredVolume > section.getMaximumCapacity()) {
            throw new SectionCapacityExceededException(section.getId());
        }
    }

    private Map<Long, Double> buildCurrentVolumeMap() {
        List<Object[]> volumeData = drinkRepository.getCurrentVolumesBySection();
        return volumeData.stream().collect(Collectors.toMap(
                entry -> (Long) entry[0],
                entry -> (Double) entry[1]
        ));
    }
}
