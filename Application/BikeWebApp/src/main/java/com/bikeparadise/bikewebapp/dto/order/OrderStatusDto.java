package com.bikeparadise.bikewebapp.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class OrderStatusDto {
    Integer id;
    String status;
}