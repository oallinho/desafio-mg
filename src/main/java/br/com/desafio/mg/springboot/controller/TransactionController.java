package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.TransactionDTO;
import br.com.desafio.mg.springboot.dto.DrinkTransferDTO;
import br.com.desafio.mg.springboot.enums.TransactionType;
import br.com.desafio.mg.springboot.security.user.CustomUserDetails;
import br.com.desafio.mg.springboot.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

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
            @RequestParam(required = false) String type,
            @AuthenticationPrincipal CustomUserDetails userDetails) {

        TransactionType transactionType = (type != null) ? TransactionType.valueOf(type) : null;
        String responsible = userDetails.getUsername();

        List<TransactionDTO> transactions = transactionService.findTransactions(transactionType, responsible);
        return ResponseEntity.ok(transactions);
    }

    @PostMapping("/transfer")
    public TransactionDTO transferDrink(
            @RequestBody DrinkTransferDTO drink,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return transactionService.transferDrink(drink, userDetails.getUsername());
    }
}

