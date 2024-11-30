package com.bikeparadise.bikewebapp.dto.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BikeAddFiltersDto {
    String parameter;
    String attribute;
}
