package com.bikeparadise.bikewebapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ReviewPrintDto {
    Integer id;
    String firstName;
    String lastName;
    int numberOfStars;
    String description;
}
