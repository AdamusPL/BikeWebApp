package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class OrderListPartDto {
    Integer id;
    String fullname;
    BigDecimal price;
    Integer quantity;
}
