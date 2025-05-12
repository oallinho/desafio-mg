package br.com.desafio.mg.springboot.listener;

import br.com.desafio.mg.springboot.enums.TransactionType;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;


@Getter
@Setter
public class DrinkEvent extends ApplicationEvent {
    private Long drinkId;
    private Long sectionId;
    private String responsible;
    private TransactionType type;

    public DrinkEvent(Object source, Long drinkId, Long sectionId) {
        super(source);
        this.drinkId = drinkId;
        this.sectionId = sectionId;
        this.responsible = responsible;
        this.type = type;
    }

}
