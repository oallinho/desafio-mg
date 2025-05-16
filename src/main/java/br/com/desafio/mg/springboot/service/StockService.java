package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.dto.StockDTO;
import br.com.desafio.mg.springboot.exceptions.stock.StockNotFoundException;
import br.com.desafio.mg.springboot.model.StockModel;
import br.com.desafio.mg.springboot.repository.StockRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

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

    public StockDTO getStockById(Long id) {
        StockModel stockModel = findStockOrThrow(id);

        return new StockDTO(stockModel);
    }

    public StockDTO save(StockDTO stock) {
        StockModel stockModel = stock.toModel();

        StockModel savedStock = stockRepository.save(stockModel);

        return new StockDTO(savedStock);
    }

    public void deleteStock(Long id) {
        StockModel stock = findStockOrThrow(id);
        stockRepository.delete(stock);
    }

    StockModel findStockOrThrow(Long id) {
        return stockRepository.findById(id).orElseThrow(() -> new StockNotFoundException(id));
    }
}
