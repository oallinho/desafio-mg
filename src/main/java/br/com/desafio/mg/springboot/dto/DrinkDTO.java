package br.com.desafio.mg.springboot.dto;

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
    private Double volume;
    private String name;
    private Long stockId;

    public DrinkDTO(DrinkModel drink) {
        this.id = drink.getId();
        this.type = drink.getType();
        this.volume = drink.getVolume();
        this.name = drink.getName();
        this.stockId = drink.getSection().getStock().getId();
    }
}
