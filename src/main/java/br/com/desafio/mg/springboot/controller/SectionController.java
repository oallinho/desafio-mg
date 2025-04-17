package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.service.DrinkService;
import br.com.desafio.mg.springboot.service.SectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/section")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionModel> getAllSections() {
        return sectionService.getAllSections();
    }

    @PostMapping
    public SectionModel createSection(@RequestBody SectionModel section) {
        return sectionService.saveSection(section);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionModel> getSectionById(@PathVariable Long id) {
        return sectionService.getSectionById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }
}
