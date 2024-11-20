package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.Map;

@Getter
@AllArgsConstructor
public class PartShopDto {
    Integer id;
    String fullModelName;
    String type;
    String attribute;
    BigDecimal price;
    Integer quantityInStock;
}