package br.com.desafio.mg.springboot.exceptions.drink;

import br.com.desafio.mg.springboot.enums.DrinkType;

import java.util.List;

public class DivergentDrinkTypeException extends RuntimeException {

    public DivergentDrinkTypeException(DrinkType validType) {
        super("The select section only accept " + validType + " drinks");
    }
}

