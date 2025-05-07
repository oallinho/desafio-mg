package br.com.desafio.mg.springboot.exceptions.stock;

public class StockNotFoundException extends RuntimeException{
    public StockNotFoundException(Long stockId) {
        super("Stock with ID " + stockId + " not found");
    }
}
