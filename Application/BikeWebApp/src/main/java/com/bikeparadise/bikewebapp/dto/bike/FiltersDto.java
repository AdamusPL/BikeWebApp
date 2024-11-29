package com.bikeparadise.bikewebapp.dto.bike;

import com.bikeparadise.bikewebapp.dto.bike.FilterCheckboxDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class FiltersDto {
    List<FilterCheckboxDto> filterCheckboxDtos;
    BigDecimal minPrice;
    BigDecimal maxPrice;
}
