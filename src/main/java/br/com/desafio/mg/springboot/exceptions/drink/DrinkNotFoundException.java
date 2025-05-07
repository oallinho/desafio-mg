package br.com.desafio.mg.springboot.exceptions.drink;

public class DrinkNotFoundException extends RuntimeException {
    public DrinkNotFoundException(Long drinkId) {
        super("Drink with ID " + drinkId + " not found");
    }
}
