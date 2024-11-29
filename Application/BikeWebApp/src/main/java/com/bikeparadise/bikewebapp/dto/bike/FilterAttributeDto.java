package com.bikeparadise.bikewebapp.dto.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FilterAttributeDto {
    Integer id;
    String attribute;
    boolean isChecked;
}
