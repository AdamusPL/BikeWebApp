package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class BikeParameterAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String attribute;

    @ManyToOne
    @JoinColumn(name = "BikeParameterTypeId")
    private BikeParameterType bikeParameterType;

    @OneToMany(mappedBy = "bikeParameterAttribute")
    private List<BikeAttribute> bikeAttribute;
}
