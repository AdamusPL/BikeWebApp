package com.bikeparadise.bikewebapp.dto.bike;


import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class BikeFilterCheckboxDto {
    Integer id;
    String type;
    List<BikeFilterAttributeDto> attribute;
}
