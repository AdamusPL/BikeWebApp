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

}
