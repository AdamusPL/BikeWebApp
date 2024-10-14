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
public class Part {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String make;
    String modelName;
    Double price;
    Integer quantityInStock;
    String description;

    @ManyToOne
    @JoinColumn(name = "PartTypeId")
    private PartType partType;

    @ManyToMany
    @JoinTable(
            name = "Part_PartAttribute",
            joinColumns = @JoinColumn(name = "PartId"),
            inverseJoinColumns = @JoinColumn(name = "PartAttributeId"))
    private List<PartAttribute> partAttribute;

    @ManyToMany
    @JoinTable(
            name = "Bike_Part",
            joinColumns = @JoinColumn(name = "PartId"),
            inverseJoinColumns = @JoinColumn(name = "BikeId"))
    List<Bike> bike;

    @ManyToOne
    @JoinColumn(name = "ShopAssistantId")
    ShopAssistant shopAssistant;

    @OneToMany(mappedBy = "part")
    private List<Review> review;

    @ManyToMany
    @JoinTable(
            name = "Order_Part",
            joinColumns = @JoinColumn(name = "PartId"),
            inverseJoinColumns = @JoinColumn(name = "OrderId"))
    private List<Order> order;

    public Part(String make, String modelName, Double price, Integer quantityInStock, String description, PartType partType, ShopAssistant shopAssistant){
        this.make = make;
        this.modelName = modelName;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
        this.partType = partType;
        this.shopAssistant = shopAssistant;
    }

}
