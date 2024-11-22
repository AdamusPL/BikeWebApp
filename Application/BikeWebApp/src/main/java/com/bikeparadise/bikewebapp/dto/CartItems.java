package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class CartItems {
    List<BikeCartInfo> bikes;
    List<PartCartInfo> parts;
}
