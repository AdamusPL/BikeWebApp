package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class PartDetailedInfoDto {
    String make;
    String modelName;
    Double price;
    String description;
    List<String> partAttributes;
    List<ReviewPrintDto> reviews;
}
