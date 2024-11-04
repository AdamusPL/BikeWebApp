package com.bikeparadise.bikewebapp.dto;

import org.springframework.beans.factory.annotation.Autowired;

public class SecurityFilterDto {
    private String accessToken;
    private String tokenType = "Bearer ";

    public SecurityFilterDto(String accessToken){
        this.accessToken = accessToken;
    }
}
