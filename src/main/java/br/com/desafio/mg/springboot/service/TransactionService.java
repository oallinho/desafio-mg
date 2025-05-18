package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.dto.TransactionDTO;
import br.com.desafio.mg.springboot.dto.DrinkTransferDTO;
import br.com.desafio.mg.springboot.enums.TransactionType;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkNotFoundException;
import br.com.desafio.mg.springboot.exceptions.section.SectionNotFoundException;
import br.com.desafio.mg.springboot.exceptions.transaction.DrinkAlreadyInSectionException;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.model.TransactionModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import br.com.desafio.mg.springboot.repository.SectionRepository;
import br.com.desafio.mg.springboot.repository.TransactionRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final DrinkRepository drinkRepository;
    private final SectionRepository sectionRepository;
    private final DrinkService drinkService;

    public TransactionService(TransactionRepository transactionRepository,
            DrinkRepository drinkRepository,
            SectionRepository sectionRepository,
            DrinkService drinkService) {
        this.transactionRepository = transactionRepository;
        this.drinkRepository = drinkRepository;
        this.sectionRepository = sectionRepository;
        this.drinkService = drinkService;
    }

    public TransactionDTO registerTransaction(Long drinkId, Long sectionId, String responsible, TransactionType type, String message) {
        DrinkModel drink = drinkRepository.findById(drinkId)
                .orElseThrow(() -> new DrinkNotFoundException(drinkId));

        SectionModel section = sectionRepository.findById(sectionId)
                .orElseThrow(() -> new SectionNotFoundException(sectionId));

        TransactionModel transaction = new TransactionModel();

        transaction.setDrink(drink);
        transaction.setSection(section);
        transaction.setStock(section.getStock());
        transaction.setVolume(drink.getVolume());
        transaction.setResponsible(responsible);
        transaction.setMessage(message);
        transaction.setType(type);
        transaction.setCreatedAt(LocalDateTime.now());

        TransactionModel saved = transactionRepository.save(transaction);

        return toDTO(saved);
    }

    @Transactional
    public TransactionDTO transferDrink(DrinkTransferDTO request, String responsible) {
        Long drinkId = request.getIdDrink();
        Long newSectionId = request.getNewSectionId();

        DrinkModel drink = drinkRepository.findById(drinkId)
                .orElseThrow(() -> new DrinkNotFoundException(drinkId));

        if(Objects.equals(drink.getSection().getId(), request.getNewSectionId()))
            throw new DrinkAlreadyInSectionException();

        SectionModel section = sectionRepository.findById(newSectionId)
                .orElseThrow(() -> new SectionNotFoundException(newSectionId));

        Long originSectionId = drink.getSection().getId();

        registerTransaction(drinkId, originSectionId, responsible, TransactionType.EXIT,
                String.format("Drink transferred to Section %d", newSectionId));

        drinkService.updateDrink(drinkId, newSectionId);


        registerTransaction(drinkId, newSectionId, responsible, TransactionType.ENTRY,
                String.format("Drink received from Section %d", originSectionId));

        return registerTransaction(drinkId, newSectionId, responsible, TransactionType.TRANSFER,
                String.format("Drink transferred from Section %d to Section %d", originSectionId, newSectionId));
    }

    public List<TransactionDTO> findTransactions(TransactionType type, String responsible) {
        List<TransactionModel> transactions = transactionRepository
                .findWithFilters(type, responsible);
        return transactions.stream().map(this::toDTO).toList();
    }

    private TransactionDTO toDTO(TransactionModel transaction) {
        return new TransactionDTO(transaction);
    }
}
