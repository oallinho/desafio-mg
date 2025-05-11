package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.model.StockModel;
import br.com.desafio.mg.springboot.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class StockService {

    private final StockRepository stockRepository;

    @Autowired
    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    public List<StockModel> getAllStocks() {
        return stockRepository.findAll();
    }

    public Optional<StockModel> getStockById(Long id) {
        return stockRepository.findById(id);
    }

    public StockModel save(StockModel stock) {
        return stockRepository.save(stock);
    }

    public void deleteStock(Long id) {
        stockRepository.deleteById(id);
    }
}
