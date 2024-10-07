package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class OrderDto {
    Integer clientId;
    List<Integer> bikeIds;
    List<Integer> partIds;
}
