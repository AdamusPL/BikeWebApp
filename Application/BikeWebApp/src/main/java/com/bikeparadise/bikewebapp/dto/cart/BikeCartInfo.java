package com.bikeparadise.bikewebapp.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class BikeCartInfo {
    Integer id;
    String fullModelName;
    BigDecimal price;
    Integer quantity;
    Integer quantityInStock;
}
