package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class OrderListDto {
    String date;
    String status;
    List<String> products;
    Double price;
}
