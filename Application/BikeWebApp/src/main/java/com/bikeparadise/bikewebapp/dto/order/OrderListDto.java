package com.bikeparadise.bikewebapp.dto.order;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@AllArgsConstructor
public class OrderListDto {
    Integer id;
    String date;
    String status;
    List<OrderListBikeDto> orderedBikes;
    List<OrderListPartDto> orderedParts;
    BigDecimal price;
}
