package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CartDto {
    List<BikeCartDto> bikes;
    List<PartCartDto> parts;
}