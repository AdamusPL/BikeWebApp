package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Getter
public class BikeAddDto {
    String modelName;
    BigDecimal price;
    String description;
    String bikeIdentificationsAvailable;
    List<BikeAddFiltersDto> parts;
    Integer shopAssistantId;
}
