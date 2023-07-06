package com.pizzeria.persistence.repository;

import com.pizzeria.persistence.entity.PizzaEntity;
import com.pizzeria.service.dto.UpdatePizzaPriceDto;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PizzaRepository extends ListCrudRepository<PizzaEntity, Integer> {
    List<PizzaEntity> findAllByAvailableTrueOrderByPrice();

    PizzaEntity findAllByAvailableTrueAndNameIgnoreCase(String name);
    //findFirstByAvailableTrueAndNameIgnoreCase => para garantizar que devuelva el primer resultado

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionContainingIgnoreCase(String ingredient);

    List<PizzaEntity> findAllByAvailableTrueAndDescriptionNotContainingIgnoreCase(String ingredient);

    List<PizzaEntity> findTop3ByAvailableTrueAndPriceLessThanEqualOrderByPriceAsc(double price);

    //obtener la cantidad de pizzas veganas
    int countByVeganTrue();

    //@Modify
    @Query(value = "UPDATE pizza " +
                    "SET price = :#{#updatePizzaPriceDto.newPrice} " +
                    "WHERE id_pizza = :#{#updatePizzaPriceDto.idPizza}", nativeQuery = true)
    @Modifying
    void updatePrice(@Param("updatePizzaPriceDto") UpdatePizzaPriceDto updatePizzaPriceDto);
}
