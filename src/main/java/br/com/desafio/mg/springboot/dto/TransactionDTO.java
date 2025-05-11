package br.com.desafio.mg.springboot.dto;

import br.com.desafio.mg.springboot.enums.TransactionType;
import br.com.desafio.mg.springboot.model.TransactionModel;

import java.time.LocalDateTime;

public class TransactionDTO {
    private Long id;
    private String drinkName;
    private String sectionName;
    private TransactionType type;
    private Double volume;
    private String responsible;
    private LocalDateTime createdAt;

    public TransactionDTO(TransactionModel transaction) {
        this.id = transaction.getId();
        this.drinkName = transaction.getDrink().getName();
        this.type = transaction.getType();
        this.volume = transaction.getVolume();
        this.responsible = transaction.getResponsible();
        this.createdAt = transaction.getCreatedAt();
    }
}
