package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.dto.DrinkDTO;
import br.com.desafio.mg.springboot.dto.request.DrinkRequest;
import br.com.desafio.mg.springboot.enums.DrinkType;
import br.com.desafio.mg.springboot.exceptions.drink.DrinkNotFoundException;
import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/drink")
public class DrinkController {

    private final DrinkService drinkService;

    @Autowired
    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping
    public ResponseEntity<List<DrinkDTO>> getAllDrinks() {
        List<DrinkModel> drinks = drinkService.getAllDrinks();
        List<DrinkDTO> response = drinks.stream().map(DrinkDTO::new).toList();

        return ResponseEntity.ok(response);
    }

    @GetMapping(("/drinks-by-section/{sectionId}"))
    public ResponseEntity<List<DrinkDTO>> getDrinksBySectionId(@PathVariable Long sectionId) {
        List<DrinkModel> drinks = drinkService.getDrinksBySection(sectionId);
        List<DrinkDTO> response = drinks.stream().map(DrinkDTO::new).toList();

        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<DrinkModel> createDrink(@RequestBody DrinkRequest request) {
        DrinkModel created = drinkService.createDrink(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @PutMapping("/{drinkId}/sell")
    public ResponseEntity<Void> sellDrink(@PathVariable Long drinkId) {
        drinkService.sellDrink(drinkId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrinkModel> getDrinkById(@PathVariable Long id) {
        return drinkService.getDrinkById(id).map(ResponseEntity::ok).orElseThrow(() -> new DrinkNotFoundException(id));

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrink(@PathVariable Long id) {
        drinkService.deleteDrink(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/volume-by-type")
    public ResponseEntity<Map<DrinkType, Double>> getTotalVolumeByType() {
        Map<DrinkType, Double> volumeMap = drinkService.getTotalVolumeByType();
        return ResponseEntity.ok(volumeMap);
    }

}
