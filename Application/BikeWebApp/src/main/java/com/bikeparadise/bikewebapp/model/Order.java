package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
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

    @OneToMany(mappedBy = "order")
    private List<PartReserved> partReserved;

    @ManyToMany
    @JoinTable(
            name = "BikeIdentificationReserved_Order",
            joinColumns = @JoinColumn(name = "OrderId"),
            inverseJoinColumns = @JoinColumn(name = "BikeIdentificationReservedId"))
    List<BikeIdentificationReserved> bikeIdentificationReserved;

    public Order(Date orderDate, Client client, OrderStatus orderStatus){
        this.orderDate = orderDate;
        this.client = client;
        this.orderStatus = orderStatus;
    }
}
