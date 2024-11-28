package com.bikeparadise.bikewebapp.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PartTypeFilterDto {
    Integer id;
    String type;
    boolean isChecked;
}
