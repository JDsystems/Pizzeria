package com.pizzeria.service.dto;

import lombok.Data;

@Data
public class UpdatePizzaPriceDto {
    int idPizza;
    double newPrice;
}
