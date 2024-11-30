package com.bikeparadise.bikewebapp.dto.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BikeFilterAttributeDto {
    Integer id;
    String attribute;
    boolean isChecked;
}
