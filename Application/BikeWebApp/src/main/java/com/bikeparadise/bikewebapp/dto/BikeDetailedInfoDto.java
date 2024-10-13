package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BikeDetailedInfoDto {
    Integer id;
    String fullModelName;
    Integer quantityInStock;
    Double price;
    String description;
    Map<String, String> parts;
    List<ReviewPrintDto> reviews;
}