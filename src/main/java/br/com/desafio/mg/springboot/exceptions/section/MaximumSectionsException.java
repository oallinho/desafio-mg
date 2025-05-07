package br.com.desafio.mg.springboot.exceptions.section;

import br.com.desafio.mg.springboot.enums.DrinkType;

public class MaximumSectionsException extends RuntimeException {

    public MaximumSectionsException(String message) {
        super(message);
    }
}

