package br.com.desafio.mg.springboot.exceptions.transaction;

public class DrinkAlreadyInSectionException extends RuntimeException{
    public DrinkAlreadyInSectionException() {
            super("The drink is already in the informed section");
    }
}
