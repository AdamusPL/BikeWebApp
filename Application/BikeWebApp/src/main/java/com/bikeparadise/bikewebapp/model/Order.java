package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "\"Order\"")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    Date orderDate;

    @ManyToOne
    @JoinColumn(name = "ClientId")
    Client client;

    @ManyToOne
    OrderStatus orderStatus;

    @ManyToMany
    @JoinTable(
            name = "Order_Part",
            joinColumns = @JoinColumn(name = "OrderId"),
            inverseJoinColumns = @JoinColumn(name = "PartId"))
    List<Part> part;

    @ManyToMany
    @JoinTable(
            name = "BikeIdentificationReserved_Order",
            joinColumns = @JoinColumn(name = "OrderId"),
            inverseJoinColumns = @JoinColumn(name = "BikeIdentificationReservedId"))
    List<BikeIdentificationReserved> bikeIdentificationReserved;

    public Order(Date orderDate, Client client, OrderStatus orderStatus, List<Part> part, List<BikeIdentificationReserved> bikeIdentificationReserved){
        this.orderDate = orderDate;
        this.client = client;
        this.orderStatus = orderStatus;
        this.part = part;
        this.bikeIdentificationReserved = bikeIdentificationReserved;
    }
}
