package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderDto {
    List<OrderItemDto> bikes;
    List<OrderItemDto> parts;
}
