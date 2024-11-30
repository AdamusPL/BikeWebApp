package com.bikeparadise.bikewebapp.dto.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@AllArgsConstructor
public class BikeShopDto {
    Integer id;
    String make;
    String modelName;
    String type;
    String drive;
    BigDecimal price;
    Integer quantityInStock;
}
