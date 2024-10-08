package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
public class BikeDetailedInfoDto {
    String make;
    String modelName;
    String type;
    Double price;
    String frameSize;
    Map<String, String> parts;
}
