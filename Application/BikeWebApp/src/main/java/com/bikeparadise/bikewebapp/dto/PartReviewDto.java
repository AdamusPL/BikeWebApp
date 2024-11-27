package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

@Getter
public class PartReviewDto {
    Integer numberOfStars;
    String description;
    Integer clientId;
    Integer partId;
}
