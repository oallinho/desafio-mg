package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.model.StockModel;
import jakarta.persistence.Column;
import lombok.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class StockDTO {

    private Long id;

    private int maximumSections;

    private double alcoholicMaximum;

    private double nonAlcoholicMaximum;

    public StockDTO (StockModel stock){
        this.id = stock.getId();
        this.maximumSections = stock.getMaximumSections();
        this.alcoholicMaximum = stock.getAlcoholicMaximum();
        this.nonAlcoholicMaximum = stock.getNonAlcoholicMaximum();

    }

}
