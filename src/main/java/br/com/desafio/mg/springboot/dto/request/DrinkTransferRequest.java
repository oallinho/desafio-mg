package br.com.desafio.mg.springboot.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DrinkTransferRequest {
    private Long idDrink;
    private Long newSectionId;
}
