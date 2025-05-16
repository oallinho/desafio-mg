package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.StockDTO;
import br.com.desafio.mg.springboot.model.StockModel;
import br.com.desafio.mg.springboot.security.user.CustomUserDetails;
import br.com.desafio.mg.springboot.service.SectionService;
import br.com.desafio.mg.springboot.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<List<StockDTO>> getAllStocks(
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<StockModel> stock = stockService.getAllStocks();
        List<StockDTO> response = stock.stream()
                .map(StockDTO::new)
                .toList();

        return ResponseEntity.ok(response);
    }



    @PostMapping
    public StockDTO createStock(@RequestBody StockDTO stock,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        return stockService.save(stock);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StockDTO> getStockById(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        StockDTO stock = stockService.getStockById(id);
        return ResponseEntity.ok(stock);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStock(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        stockService.deleteStock(id);
        return ResponseEntity.noContent().build();
    }

}
