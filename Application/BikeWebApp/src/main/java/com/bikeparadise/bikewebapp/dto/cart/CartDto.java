package com.bikeparadise.bikewebapp.dto.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartDto {
    List<BikeCartDto> bikes;
    List<PartCartDto> parts;
}
