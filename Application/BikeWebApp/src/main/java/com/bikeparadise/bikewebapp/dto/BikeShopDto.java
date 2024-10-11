package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BikeShopDto {
    Integer id;
    String fullModelName;
    String type;
    String drive;
    Double price;
}
