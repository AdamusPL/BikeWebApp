package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserContact {
    @Id
    Integer id;
    String phoneNumber;
    String email;

    @OneToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;

}
