package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    @Id
    Integer id;
    String status;

    @ManyToOne
    @JoinColumn(name = "OrderId")
    Order order;

}
