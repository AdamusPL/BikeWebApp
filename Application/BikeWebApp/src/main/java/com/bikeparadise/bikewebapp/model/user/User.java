package com.bikeparadise.bikewebapp.model.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "\"User\"")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique=true)
    String username;
    String password;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "UserDataId")
    UserData userData;

    public User(String username, String password, UserData userData) {
        this.username = username;
        this.password = password;
        this.userData = userData;
    }
}
