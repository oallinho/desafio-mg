package br.com.desafio.mg.springboot.validator;

import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SectionCapacityValidator {
    private final DrinkRepository drinkRepository;

    public SectionCapacityValidator(DrinkRepository drinkRepository) {
        this.drinkRepository = drinkRepository;
    }

    public boolean hasCapacity(SectionModel section, double requiredVolume) {
        Map<Long, Double> sectionVolumeMap = buildCurrentVolumeMap();
        double currentVolume = sectionVolumeMap.getOrDefault(section.getId(), 0.0);
        return (currentVolume + requiredVolume) <= section.getMaximumCapacity();
    }

    private Map<Long, Double> buildCurrentVolumeMap() {
        List<Object[]> volumeData = drinkRepository.getCurrentVolumesBySection();
        return volumeData.stream().collect(Collectors.toMap(
                entry -> (Long) entry[0],
                entry -> (Double) entry[1]
        ));
    }
}
