package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.DrinkDTO;
import br.com.desafio.mg.springboot.dto.StockDTO;
import br.com.desafio.mg.springboot.dto.request.StockRequest;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.model.StockModel;
import br.com.desafio.mg.springboot.security.CustomUserDetails;
import br.com.desafio.mg.springboot.service.SectionService;
import br.com.desafio.mg.springboot.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;
    private final SectionService sectionService;

    @Autowired
    public StockController(StockService stockService, SectionService sectionService) {
        this.stockService = stockService;
        this.sectionService = sectionService;
    }

    @GetMapping
    public ResponseEntity<List<StockDTO>> getAllStocks(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<StockModel> stock = stockService.getAllStocks();
        List<StockDTO> response = stock.stream()
                .map(StockDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }



    @PostMapping
    public StockModel createStock(@RequestBody StockModel stock,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return stockService.save(stock);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockModel> getStockById(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return stockService.getStockById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

}
