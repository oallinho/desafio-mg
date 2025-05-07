package br.com.desafio.mg.springboot.exceptions.section;

public class SectionNotFoundException extends RuntimeException {
    public SectionNotFoundException(Long sectionId) {
        super("Section with ID " + sectionId + " not found");
    }
}
