package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class PartDetailedInfoDto {
    Integer id;
    String fullModelName;
    BigDecimal price;
    Integer quantityInStock;
    String description;
    String type;
    String attribute;
    List<ReviewPrintDto> reviews;
}