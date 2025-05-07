package br.com.desafio.mg.springboot.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StockRequest {
    @NotNull
    private Integer maximumSections;

    @NotNull
    private Double alcoholicMaximum;

    @NotNull
    private Double nonAlcoholicMaximum;
}
