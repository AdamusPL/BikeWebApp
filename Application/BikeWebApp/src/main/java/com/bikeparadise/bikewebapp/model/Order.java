package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"Order\"")
public class Order {
    @Id
    Integer id;

    @ManyToMany
    @JoinTable(
            name = "Order_Bike",
            joinColumns = @JoinColumn(name = "OrderId"),
            inverseJoinColumns = @JoinColumn(name = "BikeId"))
    List<Bike> bike;

    @ManyToMany
    @JoinTable(
            name = "Order_Part",
            joinColumns = @JoinColumn(name = "OrderId"),
            inverseJoinColumns = @JoinColumn(name = "PartId"))
    List<Part> part;
}
