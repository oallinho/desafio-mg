package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.DrinkDTO;
import br.com.desafio.mg.springboot.dto.TransactionDTO;
import br.com.desafio.mg.springboot.dto.request.DrinkRequest;
import br.com.desafio.mg.springboot.dto.request.DrinkTransferRequest;
import br.com.desafio.mg.springboot.dto.request.TransactionRequest;
import br.com.desafio.mg.springboot.enums.TransactionType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.TransactionModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import br.com.desafio.mg.springboot.service.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/transaction")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @GetMapping("/get-all")
    public ResponseEntity<List<TransactionDTO>> getTransactions(
            @RequestParam(required = false) TransactionType type,
            @RequestParam(required = false) String responsible,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        List<TransactionDTO> transactions = transactionService
                .findTransactions(type, responsible, startDate, endDate);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transfer")
    public TransactionModel transferDrink(@RequestBody DrinkTransferRequest drink) {
        return transactionService.transferDrink(drink);
    }

}

