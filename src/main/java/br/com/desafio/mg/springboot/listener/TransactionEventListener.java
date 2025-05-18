package br.com.desafio.mg.springboot.listener;

import br.com.desafio.mg.springboot.service.SectionService;
import br.com.desafio.mg.springboot.service.TransactionService;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
public class TransactionEventListener implements ApplicationListener<TransactionEvent> {
    private final TransactionService transactionService;

    public TransactionEventListener(TransactionService transactionService, SectionService sectionService) {
        this.transactionService = transactionService;
    }

    @Override
    public void onApplicationEvent(TransactionEvent event) {
        transactionService.registerTransaction(
                event.getDrinkId(),
                event.getSectionId(),
                event.getResponsible(),
                event.getType(),
                event.getMessage()
        );
    }
}
