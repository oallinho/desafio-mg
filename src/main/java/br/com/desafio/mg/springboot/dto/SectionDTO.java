package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.SectionModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class SectionDTO {

    public SectionDTO(SectionModel section){
        BeanUtils.copyProperties(section, this);
    }

    private UUID id;

    private DrinkType permittedType;

    private Double maximumCapacity;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
