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
public class BikeParameterAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String attribute;

    @OneToMany
    List<BikeParameterType> bikeParameterType;

    @ManyToMany
    @JoinTable(
            name = "Bike_BikeParameterAttribute",
            joinColumns = @JoinColumn(name = "BikeParameterAttributeId"),
            inverseJoinColumns = @JoinColumn(name = "BikeId"))
    private List<Bike> bike;
}