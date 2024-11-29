package com.bikeparadise.bikewebapp.dto.part;

import com.bikeparadise.bikewebapp.dto.bike.BikeFilterCheckboxDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@AllArgsConstructor
@Getter
public class PartFiltersDto {
    List<PartTypeFilterDto> partTypeFilterDtos;
    BigDecimal minPrice;
    BigDecimal maxPrice;
}
