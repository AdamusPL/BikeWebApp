package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BikeIdentificationAvailable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String serialNumber;

    public BikeIdentificationAvailable(String serialNumber){
        this.serialNumber = serialNumber;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BikeId")
    Bike bike;
}
