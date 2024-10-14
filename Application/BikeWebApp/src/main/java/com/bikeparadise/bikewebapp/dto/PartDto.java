package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

@Getter
public class PartDto {
    String make;
    String modelName;
    Double price;
    Integer quantityInStock;
    String description;
    String type;
    String attribute;
    Integer shopAssistantId;
}
