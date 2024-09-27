package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"User\"")
public class User {
    @Id
    String username;
    String password;

    @OneToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;
}
