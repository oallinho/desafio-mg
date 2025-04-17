package br.com.desafio.mg.springboot.controller;

import br.com.desafio.mg.springboot.model.DrinkModel;
import br.com.desafio.mg.springboot.service.DrinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/drink")
public class DrinkController {

    private final DrinkService drinkService;

    @Autowired
    public DrinkController(DrinkService drinkService) {
        this.drinkService = drinkService;
    }

    @GetMapping
    public List<DrinkModel> getAllDrinks() {
        return drinkService.getAllDrinks();
    }

    @PostMapping
    public DrinkModel createDrink(@RequestBody DrinkModel drink) {
        return drinkService.saveDrink(drink);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DrinkModel> getDrinkById(@RequestBody Long id) {
        return drinkService.getDrinkById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDrink(@RequestBody Long id) {
        drinkService.deleteDrink(id);
        return ResponseEntity.noContent().build();
    }
}
