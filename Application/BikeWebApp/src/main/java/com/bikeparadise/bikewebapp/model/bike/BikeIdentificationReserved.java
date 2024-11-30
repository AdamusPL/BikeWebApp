package com.bikeparadise.bikewebapp.model.bike;

import com.bikeparadise.bikewebapp.model.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BikeIdentificationReserved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String make;
    String modelName;

    @Column(unique=true)
    String serialNumber;
    BigDecimal price;

    @ManyToOne
    @JoinColumn(name = "BikeId")
    Bike bike;

    @ManyToMany
    @JoinTable(
            name = "BikeIdentificationReserved_Order",
            joinColumns = @JoinColumn(name = "BikeIdentificationReservedId"),
            inverseJoinColumns = @JoinColumn(name = "OrderId"))
    List<Order> order;

    public BikeIdentificationReserved(String make, String modelName, String serialNumber, BigDecimal price, Bike bike){
        this.make = make;
        this.modelName = modelName;
        this.serialNumber = serialNumber;
        this.price = price;
        this.bike = bike;
    }
}
