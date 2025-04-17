package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.model.StockModel;
import lombok.*;
import org.springframework.beans.BeanUtils;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class StockDTO {

    public StockDTO(StockModel stock){
        BeanUtils.copyProperties(stock, this);
    }

    private UUID id;

    private Double amount;

    private OffsetDateTime createdAt;

    private OffsetDateTime updatedAt;
}
