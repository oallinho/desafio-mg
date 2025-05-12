package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.enums.TransactionType;
import br.com.desafio.mg.springboot.model.TransactionModel;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
public class TransactionDTO {
    private Long id;
    private TransactionType type;
    private Long drinkId;
    private Long sectionId;
    private Double volume;
    private String responsible;
    private LocalDateTime createdAt;

    public TransactionDTO(TransactionModel transaction) {
        this.id = transaction.getId();
        this.type = transaction.getType();
        this.drinkId = transaction.getDrink().getId();
        this.sectionId = transaction.getSection().getId();
        this.volume = transaction.getVolume();
        this.responsible = transaction.getResponsible();
        this.createdAt = transaction.getCreatedAt();
    }
}
