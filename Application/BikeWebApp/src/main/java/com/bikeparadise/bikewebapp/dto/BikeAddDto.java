package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

import java.util.Map;

@Getter
public class BikeAddDto {
    String modelName;
    Double price;
    Integer quantityInStock;
    String description;
    Integer shopAssistantId;
    Map<Integer, Integer> parts;
}