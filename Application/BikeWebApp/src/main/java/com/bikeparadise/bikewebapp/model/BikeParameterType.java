package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class BikeParameterType {
    @Id
    Integer id;
    String type;

    @ManyToMany
    @JoinTable(
            name = "Bike_BikeParameterType",
            joinColumns = @JoinColumn(name = "BikeParameterTypeId"),
            inverseJoinColumns = @JoinColumn(name = "BikeId"))
    private List<Bike> bike;

    @OneToMany(mappedBy = "bikeParameterType")
    List<BikeParameterAttribute> bikeParameterAttribute;
}
