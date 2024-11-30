package com.bikeparadise.bikewebapp.dto.bike;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class BikeAddDto {
    String modelName;
    BigDecimal price;
    String description;
    String bikeIdentificationsAvailable;
    List<BikeAddFiltersDto> parts;
    Integer shopAssistantId;
}
