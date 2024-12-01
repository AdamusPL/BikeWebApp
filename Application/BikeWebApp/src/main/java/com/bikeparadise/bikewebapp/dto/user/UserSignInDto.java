package com.bikeparadise.bikewebapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignInDto {
    String username;
    String password;
}
