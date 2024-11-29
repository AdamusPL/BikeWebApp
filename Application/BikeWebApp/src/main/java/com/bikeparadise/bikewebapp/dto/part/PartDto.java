package com.bikeparadise.bikewebapp.dto.part;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class PartDto {
    String make;
    String modelName;
    BigDecimal price;
    Integer quantityInStock;
    String description;
    String type;
    String attribute;
    Integer shopAssistantId;
}
