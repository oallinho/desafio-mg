package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.SectionDTO;
import br.com.desafio.mg.springboot.dto.request.SectionRequest;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.model.SectionModel;
import br.com.desafio.mg.springboot.service.SectionService;
import br.com.desafio.mg.springboot.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/section")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService, StockService stockService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionModel> getAllSections() {
        return sectionService.getAllSections();
    }

    @PostMapping
    public ResponseEntity<SectionModel> createSection(@RequestBody SectionRequest request) {
        SectionModel created = sectionService.createSection(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SectionModel> getSectionById(@PathVariable Long id) {
        SectionModel section = sectionService.getSectionById(id);
        return ResponseEntity.ok(section);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available-sections")
    public ResponseEntity<?> getAvailableSections(
            @RequestParam(required = false) DrinkType type,
            @RequestParam(required = false) Double volume
    ) {

        List<SectionDTO> availableSections = sectionService.getFilteredSections(type, volume);
        return availableSections.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No sections available")
                : ResponseEntity.ok(availableSections);
    }


}
