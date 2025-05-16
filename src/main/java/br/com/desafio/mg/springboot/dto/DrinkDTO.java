package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.enums.DrinkStatus;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class DrinkDTO {
    private Long id;
    private String name;
    private Double volume;
    private DrinkType type;
    private Long sectionId;
    private DrinkStatus status;

    public DrinkDTO() {}

    public DrinkDTO(DrinkModel drink) {
        this.id = drink.getId();
        this.name = drink.getName();
        this.volume = drink.getVolume();
        this.type = drink.getType();
        this.status = drink.getStatus();
        this.sectionId = drink.getSection().getId();
    }

    public DrinkModel toModel(SectionModel section) {
        DrinkModel model = new DrinkModel();
        model.setName(name);
        model.setVolume(volume);
        model.setType(type);
        model.setSection(section);
        model.setStatus(DrinkStatus.ACTIVE);
        return model;
    }
}
