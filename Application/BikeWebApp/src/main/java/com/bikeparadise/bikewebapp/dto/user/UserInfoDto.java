package com.bikeparadise.bikewebapp.dto.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserInfoDto {
    String fullname;
    String phoneNumbers;
    String emails;
    String username;
}
