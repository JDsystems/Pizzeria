package com.pizzeria.web.controller;

import com.pizzeria.persistence.entity.PizzaEntity;
import com.pizzeria.service.PizzaService;
import com.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/pizzas")
public class PizzaController {

    private final PizzaService pizzaService;

    @Autowired
    public PizzaController(PizzaService pizzaService) {
        this.pizzaService = pizzaService;
    }

    @GetMapping
    public ResponseEntity<List<PizzaEntity>> getAll() {
        return  ResponseEntity.ok(this.pizzaService.getAll());
    }

    @GetMapping("/paginate")
    public ResponseEntity<Page<PizzaEntity>> getAllPaginate(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8")int totalItems) {
        return  ResponseEntity.ok(this.pizzaService.getAllPaginate(page, totalItems));
    }

    @GetMapping("/{idPizza}")
    public ResponseEntity<PizzaEntity> get(@PathVariable int idPizza) {
        return  ResponseEntity.ok(this.pizzaService.get(idPizza));
    }

    @GetMapping("/available")
    public ResponseEntity<List<PizzaEntity>> getAvailable() {
        return  ResponseEntity.ok(this.pizzaService.getAvailable());
    }

    @GetMapping("/available/sorting")
    public ResponseEntity<Page<PizzaEntity>> getAvailableSort(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size, @RequestParam(defaultValue = "price") String sortBy) {
        return  ResponseEntity.ok(this.pizzaService.getAvailableSort(page,size, sortBy));
    }

    @GetMapping("/available/sorting-with-direction")
    public ResponseEntity<Page<PizzaEntity>> getAvailableSortWithDirection(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "8") int size,@RequestParam(defaultValue = "price") String sortBy, @RequestParam(defaultValue = "ASC") String sortDirection) {
        return  ResponseEntity.ok(this.pizzaService.getAvailableSortWithDirection(page,size, sortBy, sortDirection));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PizzaEntity> getByName(@PathVariable String name) {
        return  ResponseEntity.ok(this.pizzaService.getByName(name));
    }

    @GetMapping("/with/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWithIngredient(@PathVariable String ingredient) {
        return ResponseEntity.ok(this.pizzaService.getWithIngredient(ingredient));
    }

    @GetMapping("/without/{ingredient}")
    public ResponseEntity<List<PizzaEntity>> getWithOutIngredient(@PathVariable String ingredient) {
        return ResponseEntity.ok(this.pizzaService.getWithOutIngredient(ingredient));
    }

    @GetMapping("/cheapest/{price}")
    public ResponseEntity<List<PizzaEntity>> getCheapest(@PathVariable double price) {
        return ResponseEntity.ok(this.pizzaService.getCheapest(price));
    }

    @PostMapping
    public ResponseEntity<PizzaEntity> add(@RequestBody PizzaEntity pizza) {
        if (pizza.getIdPizza() == null || !this.pizzaService.exists(pizza.getIdPizza())) {
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }

    @PutMapping
    public ResponseEntity<PizzaEntity> update(@RequestBody PizzaEntity pizza) {
        if (pizza.getIdPizza() != null && this.pizzaService.exists(pizza.getIdPizza())) {
            return ResponseEntity.ok(this.pizzaService.save(pizza));
        }
        return ResponseEntity.badRequest().build();
    }

    @DeleteMapping("/{idPizza}")
    public ResponseEntity<Void> delete(@PathVariable int idPizza) {
        if(this.pizzaService.exists(idPizza)) {
            this.pizzaService.delete(idPizza);
            return ResponseEntity.ok().build();
        }
        return  ResponseEntity.notFound().build();
    }

    @PutMapping("/price")
    public ResponseEntity<Void> updatePrice(@RequestBody UpdatePizzaPriceDto updatePizzaPriceDto) {
        if (this.pizzaService.exists(updatePizzaPriceDto.getIdPizza())) {
            this.pizzaService.updatePrice(updatePizzaPriceDto);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
