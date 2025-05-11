package br.com.desafio.mg.springboot.exceptions.section;

public class SectionCapacityExceededException extends RuntimeException{
    public SectionCapacityExceededException(Long sectionId) {
        super("A seção com ID " + sectionId + " não possui capacidade suficiente para armazenar o volume.");
    }
}
