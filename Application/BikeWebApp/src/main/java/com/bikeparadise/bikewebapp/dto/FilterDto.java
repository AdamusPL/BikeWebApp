package com.bikeparadise.bikewebapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FilterDto {
    Integer id;
    String type;
    List<FilterAttributeDto> attribute;
}
