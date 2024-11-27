package com.bikeparadise.bikewebapp.dto;

import lombok.Getter;

@Getter
public class UserRegisterDto {
    String firstName;
    String lastName;
    String username;
    String password;
    String email;
    String phoneNumber;
    String confirmedPassword;
    boolean selectedRole;
}
