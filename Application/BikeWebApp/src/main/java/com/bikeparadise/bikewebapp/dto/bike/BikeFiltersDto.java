package com.bikeparadise.bikewebapp.dto.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class BikeFiltersDto {
    List<BikeFilterCheckboxDto> bikeFilterCheckboxDtos;
    BigDecimal minPrice;
    BigDecimal maxPrice;
}
