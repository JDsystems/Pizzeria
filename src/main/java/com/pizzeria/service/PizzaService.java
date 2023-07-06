package com.pizzeria.service;

import com.pizzeria.persistence.entity.PizzaEntity;
import com.pizzeria.persistence.repository.PizzaPagSortRepository;
import com.pizzeria.persistence.repository.PizzaRepository;
import com.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PizzaService {
    private final PizzaRepository pizzaRepository;
    private final PizzaPagSortRepository pizzaPagSortRepository;

    @Autowired
    public PizzaService(PizzaRepository pizzaRepository, PizzaPagSortRepository pizzaPagSortRepository) {
        this.pizzaRepository = pizzaRepository;
        this.pizzaPagSortRepository = pizzaPagSortRepository;
    }

    public List<PizzaEntity> getAll() {
        return this.pizzaRepository.findAll();
    }

    //usando Paging
    public Page<PizzaEntity> getAllPaginate(int page, int totalItems) {
        Pageable pageRequest = PageRequest.of(page, totalItems);
        return  this.pizzaPagSortRepository.findAll(pageRequest);
    }
    public PizzaEntity get(int idPizza) {
        return  this.pizzaRepository.findById(idPizza).orElse(null);
    }

    public List<PizzaEntity> getAvailable() {
        return this.pizzaRepository.findAllByAvailableTrueOrderByPrice();
    }

    //usando Paging y Sorting, por defecto es el ordenamiento es ASC
    public Page<PizzaEntity> getAvailableSort(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        return this.pizzaPagSortRepository.findByAvailableTrue(pageable);
    }

    public Page<PizzaEntity> getAvailableSortWithDirection(int page, int size, String sortBy, String sortDirecion) {
        Sort sort = Sort.by(Sort.Direction.fromString(sortDirecion), sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        return this.pizzaPagSortRepository.findByAvailableTrue(pageable);
    }

    public PizzaEntity getByName(String name) {
        return this.pizzaRepository.findAllByAvailableTrueAndNameIgnoreCase(name);
    }

    public  List<PizzaEntity> getWithIngredient(String ingredient) {
        return  this.pizzaRepository.findAllByAvailableTrueAndDescriptionContainingIgnoreCase(ingredient);
    }

    public  List<PizzaEntity> getWithOutIngredient(String ingredient) {
        return  this.pizzaRepository.findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(ingredient);
    }

    public  List<PizzaEntity> getCheapest(double price) {
        return  this.pizzaRepository.findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(price);
    }

    public PizzaEntity save(PizzaEntity pizza) {
        return  this.pizzaRepository.save(pizza);
    }

    public boolean exists(int idPizza) {
        return this.pizzaRepository.existsById(idPizza);
    }

    public void delete(int idPizza) {
        this.pizzaRepository.deleteById(idPizza);
    }

    @Transactional
    public  void updatePrice(UpdatePizzaPriceDto updatePizzaPriceDto) {
        this.pizzaRepository.updatePrice(updatePizzaPriceDto);
    }
}
