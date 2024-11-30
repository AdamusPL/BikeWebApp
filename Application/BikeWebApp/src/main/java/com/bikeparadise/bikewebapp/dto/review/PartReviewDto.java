package com.bikeparadise.bikewebapp.dto.review;

import lombok.Getter;

@Getter
public class PartReviewDto {
    Integer numberOfStars;
    String description;
    Integer clientId;
    Integer partId;
}
