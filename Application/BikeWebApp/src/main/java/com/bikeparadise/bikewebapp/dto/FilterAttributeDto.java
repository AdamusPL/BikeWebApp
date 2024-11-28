package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterAttributeDto {
    Integer id;
    String attribute;
    boolean isChecked;
}
