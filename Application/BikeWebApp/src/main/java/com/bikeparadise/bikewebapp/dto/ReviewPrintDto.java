package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewPrintDto {
    String firstName;
    String lastName;
    int numberOfStars;
    String description;
}
