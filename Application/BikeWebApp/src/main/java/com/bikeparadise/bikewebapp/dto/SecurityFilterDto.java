package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

@Getter
public class SecurityFilterDto {
    private String accessToken;
    private String tokenType = "Bearer ";

    public SecurityFilterDto(String accessToken){
        this.accessToken = accessToken;
    }
}
