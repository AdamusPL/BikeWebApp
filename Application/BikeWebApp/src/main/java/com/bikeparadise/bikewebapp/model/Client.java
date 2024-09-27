package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Client {
    @Id
    Integer id;

    @OneToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;

    @OneToMany
    private List<Review> review;

    @OneToMany
    private List<Bike> bike;

    @OneToMany
    private List<Part> part;
}
