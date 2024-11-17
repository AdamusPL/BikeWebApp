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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;

    @OneToMany(mappedBy = "shopAssistant")
    List<Part> part;

    @OneToMany(mappedBy = "shopAssistant")
    List<Bike> bike;

    public ShopAssistant(UserData userData){
        this.userData = userData;
    }

}
