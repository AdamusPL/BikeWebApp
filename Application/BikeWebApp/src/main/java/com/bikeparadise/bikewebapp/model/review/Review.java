package com.bikeparadise.bikewebapp.model.review;

import com.bikeparadise.bikewebapp.model.bike.Bike;
import com.bikeparadise.bikewebapp.model.part.Part;
import com.bikeparadise.bikewebapp.model.roles.Client;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
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

    public Review(Integer numberOfStars, String description, Client client, Bike bike){
        this.numberOfStars = numberOfStars;
        this.description = description;
        this.client = client;
        this.bike = bike;
    }

    public Review(Integer numberOfStars, String description, Client client, Part part){
        this.numberOfStars = numberOfStars;
        this.description = description;
        this.client = client;
        this.part = part;
    }
}
