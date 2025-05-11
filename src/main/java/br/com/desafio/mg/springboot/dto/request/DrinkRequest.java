package br.com.desafio.mg.springboot.dto.request;

import br.com.desafio.mg.springboot.enums.DrinkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrinkRequest {
    private String name;
    private Double volume;
    private DrinkType type;
    private Long sectionId;
}
