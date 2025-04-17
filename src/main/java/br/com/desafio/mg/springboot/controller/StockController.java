package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.model.StockModel;
import br.com.desafio.mg.springboot.service.SectionService;
import br.com.desafio.mg.springboot.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/stock")
public class StockController {

    private final StockService stockService;

    @Autowired
    public StockController(StockService stockService) {
        this.stockService = stockService;
    }

    @GetMapping
    public List<StockModel> getAllStocks() {
        return stockService.getAllStocks();
    }

    @PostMapping
    public StockModel createStock(@RequestBody StockModel stock) {
        return stockService.saveStock(stock);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockModel> getStockById(@PathVariable Long id) {
        return stockService.getStockById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }
}
