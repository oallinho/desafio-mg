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
        // Obter a seção do drink
        SectionModel section = sectionService.getSectionById(event.getSectionId());

        // Registrar a transação do tipo ENTRY
        transactionService.registerTransaction(
                event.getDrinkId(),
                section.getId(),
                "allan.paiva", // Você pode ajustar o responsável conforme necessário
                TransactionType.ENTRY
        );
    }
}
