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
    String description;

    @ManyToMany
    @JoinTable(
            name = "Bike_Part",
            joinColumns = @JoinColumn(name = "BikeId"),
            inverseJoinColumns = @JoinColumn(name = "PartId"))
    List<Part> part;

    @OneToMany(mappedBy = "bike")
    private List<Review> review;

    @OneToMany(mappedBy = "bike")
    List<BikeIdentificationAvailable> bikeIdentificationAvailable;

    @OneToMany(mappedBy = "bike")
    List<BikeIdentificationReserved> bikeIdentificationReserved;

    @ManyToOne
    @JoinColumn(name = "BikeTypeId")
    BikeType bikeType;

    @ManyToOne
    @JoinColumn(name = "BikeFrameSizeId")
    BikeFrameSize bikeFrameSize;

    @ManyToOne
    @JoinColumn(name = "ShopAssistantId")
    ShopAssistant shopAssistant;

    public Bike(String make, String modelName, Double price, String description) {
        this.make = make;
        this.modelName = modelName;
        this.price = price;
        this.description = description;
    }

    public Bike(String make, String modelName, Double price, String description, BikeType bikeType, BikeFrameSize bikeFrameSize, ShopAssistant shopAssistant) {
        this.make = make;
        this.modelName = modelName;
        this.price = price;
        this.description = description;
        this.bikeType = bikeType;
        this.bikeFrameSize = bikeFrameSize;
        this.shopAssistant = shopAssistant;
    }
}
