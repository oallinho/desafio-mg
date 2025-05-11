package br.com.desafio.mg.springboot.dto.request;

import br.com.desafio.mg.springboot.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionRequest {
    private Long drinkId;
    private Long sectionId;
    private Long stockId;
    private Double volume;
    private String responsible;
    private TransactionType type;
}
