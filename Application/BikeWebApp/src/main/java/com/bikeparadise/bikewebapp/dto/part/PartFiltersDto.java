package com.bikeparadise.bikewebapp.dto.part;

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
