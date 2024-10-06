package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

@Getter
public class BikeDto {
    String make;
    String modelName;
    Double price;
    Integer quantityInStock;
    String description;
    Integer shopAssistantId;
    Integer bikeTypeId;
    Integer bikeFrameSizeId;
}
