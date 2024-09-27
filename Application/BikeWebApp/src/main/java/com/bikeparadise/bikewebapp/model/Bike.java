package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
    @Id
    Integer id;
    String make;
    String modelName;
    String type;
    String price;
    String quantityInStock;
    String frameSize;
    String description;

    @OneToMany(mappedBy = "bike")
    private List<Part> part;

    @OneToMany(mappedBy = "bike")
    private List<Review> review;

    @ManyToOne
    @JoinColumn(name = "ShopAssistantId")
    ShopAssistant shopAssistant;

    @ManyToOne
    @JoinColumn(name = "ClientId")
    Client client;

    @ManyToMany
    @JoinTable(
            name = "Bike_Order",
            joinColumns = @JoinColumn(name = "BikeId"),
            inverseJoinColumns = @JoinColumn(name = "OrderId"))
    private List<Order> order;

}
