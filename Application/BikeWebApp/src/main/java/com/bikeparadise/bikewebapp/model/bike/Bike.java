package com.bikeparadise.bikewebapp.model.bike;

import com.bikeparadise.bikewebapp.model.part.Part;
import com.bikeparadise.bikewebapp.model.review.Review;
import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
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
public class Bike {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String modelName;
    BigDecimal price;
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
    @JoinColumn(name = "ShopAssistantId")
    ShopAssistant shopAssistant;

    public Bike(String modelName, BigDecimal price, String description) {
        this.modelName = modelName;
        this.price = price;
        this.description = description;
    }

    public Bike(String modelName, BigDecimal price, String description, ShopAssistant shopAssistant) {
        this.modelName = modelName;
        this.price = price;
        this.description = description;
        this.shopAssistant = shopAssistant;
    }

    @ManyToMany
    @JoinTable(
            name = "Bike_BikeAttribute",
            joinColumns = @JoinColumn(name = "BikeId"),
            inverseJoinColumns = @JoinColumn(name = "BikeAttributeId"))
    private List<BikeAttribute> bikeAttribute;
}
