package com.bikeparadise.bikewebapp.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Integer numberOfStars;
    String description;

    @ManyToOne
    @JoinColumn(name = "ClientId")
    Client client;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "BikeId")
    Bike bike;

    @Nullable
    @ManyToOne
    @JoinColumn(name = "PartId")
    Part part;
}
