package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BikeShopDto {
    Integer id;
    String fullModelName;
    String type;
    String drive;
    BigDecimal price;
    Integer quantityInStock;
}
