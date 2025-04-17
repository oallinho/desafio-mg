package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
public class DrinkDTO {

    public DrinkDTO(DrinkModel drink){
        BeanUtils.copyProperties(drink, this);
    }

    private UUID id;

    private DrinkType type;

    private Double liter;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;

}
