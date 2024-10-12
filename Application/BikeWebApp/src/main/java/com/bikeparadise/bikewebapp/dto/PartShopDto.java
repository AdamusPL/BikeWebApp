package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor
public class PartShopDto {
    Integer id;
    String fullModelName;
    String type;
    Map<String, String> attributes;
    Double price;
}
