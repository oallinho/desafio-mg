package br.com.desafio.mg.springboot.exceptions.section;

public class SectionCapacityExceededException extends RuntimeException{
    public SectionCapacityExceededException(Long sectionId) {
        super("The section with ID " + sectionId + " does not have sufficient capacity to store the volume");
    }
}
