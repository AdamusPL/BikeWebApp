package com.bikeparadise.bikewebapp.dto.part;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PartTypeFilterDto {
    Integer id;
    String type;
    boolean isChecked;
}
