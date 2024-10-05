package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserContact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String phoneNumber;
    String email;

    @ManyToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;

    public UserContact(String email, String phoneNumber) {
        this.email = email;
        this.phoneNumber = phoneNumber;
    }
}
