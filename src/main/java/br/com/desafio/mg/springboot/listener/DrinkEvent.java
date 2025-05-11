package br.com.desafio.mg.springboot.listener;

import org.springframework.context.ApplicationEvent;

public class DrinkEvent extends ApplicationEvent {
    private Long drinkId;
    private Long sectionId;

    public DrinkEvent(Object source, Long drinkId, Long sectionId) {
        super(source);
        this.drinkId = drinkId;
        this.sectionId = sectionId;
    }

    public Long getDrinkId() {
        return drinkId;
    }

    public Long getSectionId() {
        return sectionId;
    }
}
