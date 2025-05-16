package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.model.StockModel;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockDTO {

    private Long id;

    @NotNull
    private Integer maximumSections;

    @NotNull
    private Double alcoholicMaximum;

    @NotNull
    private Double nonAlcoholicMaximum;

    public StockDTO() {}

    public StockDTO(StockModel stock) {
        this.id = stock.getId();
        this.maximumSections = stock.getMaximumSections();
        this.alcoholicMaximum = stock.getAlcoholicMaximum();
        this.nonAlcoholicMaximum = stock.getNonAlcoholicMaximum();
    }

    public StockModel toModel() {
        StockModel stock = new StockModel(this.id);
        stock.setMaximumSections(this.maximumSections);
        stock.setAlcoholicMaximum(this.alcoholicMaximum);
        stock.setNonAlcoholicMaximum(this.nonAlcoholicMaximum);
        return stock;
    }
}
