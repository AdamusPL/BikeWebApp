package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PartReserved {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String make;
    String modelName;
    Integer quantity;
    BigDecimal price;

    @ManyToOne
    private Part part;

    @ManyToOne
    private Order order;

    public PartReserved(String make, String modelName, Integer quantity, BigDecimal price){
        this.make = make;
        this.modelName = modelName;
        this.quantity = quantity;
        this.price = price;
    }
}
