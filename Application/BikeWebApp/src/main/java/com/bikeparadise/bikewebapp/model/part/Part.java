package com.bikeparadise.bikewebapp.model.part;

import com.bikeparadise.bikewebapp.model.review.Review;
import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
import com.bikeparadise.bikewebapp.model.bike.Bike;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    BigDecimal price;
    Integer quantityInStock;
    String description;

    @ManyToOne(cascade = CascadeType.ALL)
    private PartParameterAttribute partParameterAttribute;

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

    @OneToMany(mappedBy = "part")
    private List<PartReserved> partReserved;

    public Part(String make, String modelName, BigDecimal price, Integer quantityInStock, String description, PartParameterAttribute partParameterAttribute, ShopAssistant shopAssistant){
        this.make = make;
        this.modelName = modelName;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
        this.partParameterAttribute = partParameterAttribute;
        this.shopAssistant = shopAssistant;
    }

    public Part(String make, String modelName, BigDecimal price, Integer quantityInStock, String description, ShopAssistant shopAssistant){
        this.make = make;
        this.modelName = modelName;
        this.price = price;
        this.quantityInStock = quantityInStock;
        this.description = description;
        this.shopAssistant = shopAssistant;
    }

}
