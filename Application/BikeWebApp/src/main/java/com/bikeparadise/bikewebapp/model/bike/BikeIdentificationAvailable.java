package com.bikeparadise.bikewebapp.model.bike;

import com.bikeparadise.bikewebapp.model.bike.Bike;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BikeIdentificationAvailable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique=true)
    String serialNumber;

    public BikeIdentificationAvailable(String serialNumber){
        this.serialNumber = serialNumber;
    }

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "BikeId")
    Bike bike;
}
