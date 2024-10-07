package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BikeIdentification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String serialNumber;

    @ManyToOne
    @JoinColumn(name = "BikeId")
    Bike bike;

    @ManyToMany
    @JoinTable(
            name = "BikeIdentification_Order",
            joinColumns = @JoinColumn(name = "OrderIdBikeIdentificationId"),
            inverseJoinColumns = @JoinColumn(name = "OrderId"))
    List<Order> order;
}
