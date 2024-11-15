package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class PartCartInfo {
    Integer id;
    String fullModelName;
    BigDecimal price;
    Integer quantity;
    Integer quantityInStock;
}
