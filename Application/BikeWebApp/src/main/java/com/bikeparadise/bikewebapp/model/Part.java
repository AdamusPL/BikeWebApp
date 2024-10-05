package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String make;
    String modelName;
    Double price;
    Integer quantityInStock;

    @ManyToOne
    @JoinColumn(name = "PartTypeId")
    PartType partType;

    @ManyToOne
    @JoinColumn(name = "BikeId")
    Bike bike;

    @ManyToOne
    @JoinColumn(name = "ShopAssistantId")
    ShopAssistant shopAssistant;

    @OneToMany
    private List<Review> review;

    @ManyToMany
    @JoinTable(
            name = "Order_Part",
            joinColumns = @JoinColumn(name = "PartId"),
            inverseJoinColumns = @JoinColumn(name = "OrderId"))
    private List<Order> order;

}
