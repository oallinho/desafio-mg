package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.SectionDTO;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.security.user.CustomUserDetails;
import br.com.desafio.mg.springboot.service.SectionService;
import br.com.desafio.mg.springboot.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/section")
public class SectionController {

    private final SectionService sectionService;

    @Autowired
    public SectionController(SectionService sectionService, StockService stockService) {
        this.sectionService = sectionService;
    }

    @GetMapping
    public List<SectionDTO> getAllSections(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return sectionService.getAllSections();
    }

    @PostMapping
    public ResponseEntity<SectionDTO> createSection(@RequestBody SectionDTO request,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        SectionDTO created = sectionService.createSection(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Optional<SectionDTO>> getSectionById(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        Optional<SectionDTO> section = sectionService.getSectionById(id);
        return ResponseEntity.ok(section);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSection(@PathVariable Long id,
            @AuthenticationPrincipal CustomUserDetails userDetails) {
        sectionService.deleteSection(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/available-sections")
    public ResponseEntity<?> getAvailableSections(
            @RequestParam(required = false) DrinkType type,
            @RequestParam(required = false) Double volume,
            @AuthenticationPrincipal CustomUserDetails userDetails
    ) {

        List<SectionDTO> availableSections = sectionService.getFilteredSections(type, volume);
        return availableSections.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("No sections available")
                : ResponseEntity.ok(availableSections);
    }
}
