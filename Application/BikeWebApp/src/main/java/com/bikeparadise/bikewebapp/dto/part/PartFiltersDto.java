package com.bikeparadise.bikewebapp.dto.part;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PartFiltersDto {
    List<PartTypeFilterDto> partTypeFilterDtos;
    String minPrice;
    String maxPrice;
}
