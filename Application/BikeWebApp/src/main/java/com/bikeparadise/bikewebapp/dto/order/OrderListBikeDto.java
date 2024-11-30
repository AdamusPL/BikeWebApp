package com.bikeparadise.bikewebapp.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class OrderListBikeDto {
    Integer id;
    String fullname;
    BigDecimal price;
    Integer quantity;
}
