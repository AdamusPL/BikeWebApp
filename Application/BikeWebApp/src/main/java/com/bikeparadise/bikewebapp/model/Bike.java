package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String make;
    String modelName;
    Double price;
    Integer quantityInStock;
    String description;

    @OneToMany(mappedBy = "bike")
    private List<Part> part;

    @OneToMany(mappedBy = "bike")
    private List<Review> review;

    @OneToMany
    List<BikeIdentification> bikeIdentification;

    @ManyToOne
    @JoinColumn(name = "BikeTypeId")
    BikeType bikeType;

    @ManyToOne
    @JoinColumn(name = "BikeFrameSizeId")
    BikeFrameSize bikeFrameSize;

    @ManyToOne
    @JoinColumn(name = "ShopAssistantId")
    ShopAssistant shopAssistant;

    public Bike(String make, String modelName, Double price, Integer quantityInStock, String description) {
        this.make = make;
        this.modelName = modelName;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
    }

    public Bike(String make, String modelName, Double price, Integer quantityInStock, String description, BikeType bikeType, BikeFrameSize bikeFrameSize, ShopAssistant shopAssistant) {
        this.make = make;
        this.modelName = modelName;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
        this.bikeType = bikeType;
        this.bikeFrameSize = bikeFrameSize;
        this.shopAssistant = shopAssistant;
    }
}
