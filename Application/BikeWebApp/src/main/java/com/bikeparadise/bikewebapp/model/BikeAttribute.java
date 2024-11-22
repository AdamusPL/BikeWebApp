package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class BikeAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @ManyToMany
    @JoinTable(
            name = "Bike_BikeAttribute",
            joinColumns = @JoinColumn(name = "BikeAttributeId"),
            inverseJoinColumns = @JoinColumn(name = "BikeId"))
    private List<Bike> bike;

    @ManyToOne
    @JoinColumn(name = "BikeParameterTypeId")
    private BikeParameterType bikeParameterType;

    @ManyToOne
    @JoinColumn(name = "BikeParameterAttributeId")
    private BikeParameterAttribute bikeParameterAttribute;
}
