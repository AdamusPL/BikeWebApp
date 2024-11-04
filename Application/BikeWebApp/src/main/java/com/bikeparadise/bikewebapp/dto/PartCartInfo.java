package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class PartCartInfo {
    Integer id;
    String fullModelName;
    Double price;
    Integer quantity;
}
