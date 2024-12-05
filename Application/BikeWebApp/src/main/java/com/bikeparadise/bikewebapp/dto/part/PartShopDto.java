package com.bikeparadise.bikewebapp.dto.part;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class PartShopDto {
    Integer id;
    String make;
    String modelName;
    String type;
    String attribute;
    BigDecimal price;
    Integer quantityInStock;
}
