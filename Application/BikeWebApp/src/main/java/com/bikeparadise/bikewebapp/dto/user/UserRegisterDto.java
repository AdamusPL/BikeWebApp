package com.bikeparadise.bikewebapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
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
