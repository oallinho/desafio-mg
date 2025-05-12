package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.enums.DrinkStatus;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import lombok.*;

@Data
@Getter
@Setter
@AllArgsConstructor
public class DrinkDTO {

    private Long id;
    private DrinkType type;
    private DrinkStatus status;
    private Double volume;
    private String name;
    private Long sectionId;
    private Long stockId;

    public DrinkDTO(DrinkModel drink) {
        this.id = drink.getId();
        this.type = drink.getType();
        this.status = drink.getStatus();
        this.volume = drink.getVolume();
        this.name = drink.getName();
        this.sectionId = drink.getSection().getId();
        this.stockId = drink.getSection().getStock().getId();
    }
}
