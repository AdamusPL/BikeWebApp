package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserPhoneNumber {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String phoneNumber;

    @ManyToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;

    public UserPhoneNumber(String phoneNumber, UserData userData) {
        this.phoneNumber = phoneNumber;
        this.userData = userData;
    }
}
