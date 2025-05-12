package br.com.desafio.mg.springboot.listener;

import br.com.desafio.mg.springboot.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class TransactionEvent extends ApplicationEvent {
    private final Long drinkId;
    private final Long sectionId;
    private final String responsible;
    private final TransactionType type;

    public TransactionEvent(Object source, Long drinkId, Long sectionId, String responsible, TransactionType type) {
        super(source);
        this.drinkId = drinkId;
        this.sectionId = sectionId;
        this.responsible = responsible;
        this.type = type;
    }
}


