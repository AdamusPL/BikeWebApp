package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    Integer id;
    Integer numberOfStars;
    String description;

    @ManyToOne
    @JoinColumn(name = "ClientId")
    Client client;

    @ManyToOne
    @JoinColumn(name = "BikeId")
    Bike bike;

    @ManyToOne
    @JoinColumn(name = "PartId")
    Part part;
}
