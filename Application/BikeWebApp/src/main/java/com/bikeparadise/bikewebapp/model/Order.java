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
    @JoinColumn(name = "BikeIdentificationId")
    BikeIdentification bikeIdentification;

    @OneToMany
    List<OrderStatus> orderStatus;

    @ManyToMany
    @JoinTable(
            name = "Order_Part",
            joinColumns = @JoinColumn(name = "OrderId"),
            inverseJoinColumns = @JoinColumn(name = "PartId"))
    List<Part> part;
}
