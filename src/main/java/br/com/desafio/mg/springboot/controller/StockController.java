package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.DrinkDTO;
import br.com.desafio.mg.springboot.dto.StockDTO;
import br.com.desafio.mg.springboot.dto.request.StockRequest;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.model.StockModel;
import br.com.desafio.mg.springboot.service.SectionService;
import br.com.desafio.mg.springboot.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<List<StockDTO>> getAllStocks() {
        List<StockModel> stock = stockService.getAllStocks();
        List<StockDTO> response = stock.stream()
                .map(StockDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }



    @PostMapping
    public StockModel createStock(@RequestBody StockModel stock) {
        return stockService.save(stock);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockModel> getStockById(@PathVariable Long id) {
        return stockService.getStockById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStock(@PathVariable Long id, @RequestBody StockRequest request) {
        Optional<StockModel> optionalStock = stockService.getStockById(id);
        if (optionalStock.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Stock not found.");
        }

        StockModel stock = optionalStock.get();

        List<SectionModel> sections = sectionService.getSectionsByStockId(stock.getId());

        for (SectionModel section : sections) {
            double newLimit = section.getPermittedType() == DrinkType.ALCOHOLIC ? request.getAlcoholicMaximum() : request.getNonAlcoholicMaximum();

            if (section.getMaximumCapacity() > newLimit) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Section " + section.getId() + " exceeds new " + section.getPermittedType().name().toLowerCase().replace("_", " ") + " limit.");
            }
        }

        stock.setAlcoholicMaximum(request.getAlcoholicMaximum());
        stock.setNonAlcoholicMaximum(request.getNonAlcoholicMaximum());
        stock.setMaximumSections(request.getMaximumSections());
        stockService.save(stock);

        for (SectionModel section : sections) {
            if (section.getPermittedType() == DrinkType.ALCOHOLIC) {
                section.setMaximumCapacity(request.getAlcoholicMaximum());
            } else {
                section.setMaximumCapacity(request.getNonAlcoholicMaximum());
            }
        }
        sectionService.saveAll(sections);

        return ResponseEntity.ok("Stock updated successfully.");
    }
}
