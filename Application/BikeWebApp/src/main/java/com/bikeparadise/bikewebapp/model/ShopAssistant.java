package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ShopAssistant {
    @Id
    Integer id;

    @OneToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;

    @OneToMany
    List<Part> part;

    @OneToMany
    List<Bike> bike;

}
