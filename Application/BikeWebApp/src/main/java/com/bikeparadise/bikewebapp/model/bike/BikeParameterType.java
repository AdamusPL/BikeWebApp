package com.bikeparadise.bikewebapp.model.bike;

import com.bikeparadise.bikewebapp.model.bike.BikeAttribute;
import com.bikeparadise.bikewebapp.model.bike.BikeParameterAttribute;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class BikeParameterType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique=true)
    String type;

    @OneToMany(mappedBy = "bikeParameterType")
    private List<BikeAttribute> bikeAttribute;

    @OneToMany(mappedBy = "bikeParameterType")
    private List<BikeParameterAttribute> bikeParameterAttribute;
}
