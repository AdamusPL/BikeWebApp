package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BikeCartInfo {
    Integer id;
    String fullModelName;
    Double price;
    Integer quantity;
}
