package br.com.desafio.mg.springboot.exceptions.drink;

import lombok.Getter;

import java.util.List;

@Getter
public class InvalidDrinkTypeException extends RuntimeException{
    private final List<String> validTypes;

    public InvalidDrinkTypeException(String providedType, List<String> validTypes) {
        super("Invalid drink type: '" + providedType + "'. Valid types are: " + validTypes);
        this.validTypes = validTypes;
    }

}
