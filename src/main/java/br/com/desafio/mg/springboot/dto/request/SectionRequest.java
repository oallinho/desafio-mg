package br.com.desafio.mg.springboot.dto.request;

import br.com.desafio.mg.springboot.enums.DrinkType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionRequest {
    private DrinkType permittedType;
    private Long stockId;
}
