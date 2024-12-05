package com.bikeparadise.bikewebapp.dto.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BikeFiltersDto {
    List<BikeFilterCheckboxDto> bikeFilterCheckboxDtos;
    String minPrice;
    String maxPrice;
}
