package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class BikeIdentificationReserved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String serialNumber;

    @ManyToOne
    @JoinColumn(name = "BikeId")
    Bike bike;

    @ManyToMany
    @JoinTable(
            name = "BikeIdentificationReserved_Order",
            joinColumns = @JoinColumn(name = "BikeIdentificationReservedId"),
            inverseJoinColumns = @JoinColumn(name = "OrderId"))
    List<Order> order;

    public BikeIdentificationReserved(String serialNumber, Bike bike){
        this.serialNumber = serialNumber;
        this.bike = bike;
    }
}
