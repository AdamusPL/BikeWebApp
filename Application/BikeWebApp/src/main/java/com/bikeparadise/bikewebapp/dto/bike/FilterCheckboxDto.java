package com.bikeparadise.bikewebapp.dto.bike;


import com.bikeparadise.bikewebapp.dto.bike.FilterAttributeDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class FilterCheckboxDto {
    Integer id;
    String type;
    List<FilterAttributeDto> attribute;
}
