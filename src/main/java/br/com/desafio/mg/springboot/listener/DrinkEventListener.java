package br.com.desafio.mg.springboot.listener;

import br.com.desafio.mg.springboot.enums.TransactionType;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.service.SectionService;
import br.com.desafio.mg.springboot.service.TransactionService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class DrinkEventListener implements ApplicationListener<DrinkEvent> {
    private final TransactionService transactionService;
    private final SectionService sectionService;

    public DrinkEventListener(TransactionService transactionService, SectionService sectionService) {
        this.transactionService = transactionService;
        this.sectionService = sectionService;
    }

    @Override
    public void onApplicationEvent(DrinkEvent event) {
        SectionModel section = sectionService.getSectionById(event.getSectionId());

        transactionService.registerTransaction(
                event.getDrinkId(),
                section.getId(),
                "allan.paiva",
                TransactionType.ENTRY
        );
    }
}
