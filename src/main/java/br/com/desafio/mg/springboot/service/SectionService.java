package br.com.desafio.mg.springboot.service;

import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.repository.DrinkRepository;
import br.com.desafio.mg.springboot.repository.SectionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SectionService {

    private final SectionRepository sectionRepository;

    @Autowired
    public SectionService(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    public List<SectionModel> getAllSections() {
        return sectionRepository.findAll();
    }

    public Optional<SectionModel> getSectionById(Long id) {
        return sectionRepository.findById(id);
    }

    public SectionModel saveSection(SectionModel drink) {
        return sectionRepository.save(drink);
    }

    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }
}
