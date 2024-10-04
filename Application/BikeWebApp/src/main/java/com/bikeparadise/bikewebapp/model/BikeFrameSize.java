package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class BikeFrameSize {
    @Id
    Integer id;
    String frameSize;

    @OneToMany
    List<Bike> bike;
}
