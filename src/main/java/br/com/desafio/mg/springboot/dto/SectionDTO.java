package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.model.StockModel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SectionDTO {

    private Long id;
    private DrinkType permittedType;
    private Double maximumCapacity;
    private Double availableCapacity;
    private Long stockId;

    public SectionDTO(){};

    public SectionDTO(SectionModel section, double currentVolume) {
        this.id = section.getId();
        this.permittedType = section.getPermittedType();
        this.maximumCapacity = section.getMaximumCapacity();
        this.availableCapacity = section.getMaximumCapacity() - currentVolume;
        this.stockId = section.getStock().getId();

    }

    public SectionDTO(SectionModel saved) {
    }

    public SectionModel toModel(StockModel stock) {
        SectionModel model = new SectionModel();
        model.setPermittedType(permittedType);
        model.setMaximumCapacity(maximumCapacity);
        model.setStock(stock);
        return model;
    }

    public void setStock(StockDTO stock) {
    }
}


