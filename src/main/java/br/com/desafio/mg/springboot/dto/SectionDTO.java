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

    private Long id;
    private DrinkType permittedType;
    private Double maximumCapacity;
    private Double availableCapacity;
    private Long stockId;

    public SectionDTO(SectionModel section, double currentVolume) {
        this.id = section.getId();
        this.permittedType = section.getPermittedType();
        this.maximumCapacity = section.getMaximumCapacity();
        this.availableCapacity = section.getMaximumCapacity() - currentVolume;
        this.stockId = section.getStock().getId();

    }
}


