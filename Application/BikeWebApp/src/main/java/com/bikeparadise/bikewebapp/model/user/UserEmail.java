package com.bikeparadise.bikewebapp.model.user;

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
public class UserEmail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique=true)
    String email;

    @ManyToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;

    public UserEmail(String email, UserData userData) {
        this.email = email;
        this.userData = userData;
    }
}
