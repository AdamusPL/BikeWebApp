package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

@Getter
public class BikeReviewDto {
    Integer numberOfStars;
    String description;
    Integer clientId;
    Integer bikeId;
}
