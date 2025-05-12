package br.com.desafio.mg.springboot.exceptions.drink;

public class DrinkAlreadySoldException extends RuntimeException{

    public DrinkAlreadySoldException(Long id){
        super("Drink " + id + " has already been sold.");
    }

}
