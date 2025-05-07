package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Getter
@Setter
@AllArgsConstructor
public class DrinkDTO {

    private Long id;
    private DrinkType type;
    private Double liter;
    private String name;
    private Long stockId;

    public DrinkDTO(DrinkModel drink) {
        this.id = drink.getId();
        this.type = drink.getType();
        this.liter = drink.getLiter();
        this.name = drink.getName();
        this.stockId = drink.getSection().getStock().getId();
    }

    public DrinkDTO(DrinkDTO drinkDTO) {
    }
}
