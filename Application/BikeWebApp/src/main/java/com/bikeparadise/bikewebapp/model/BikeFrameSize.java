package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class BikeFrameSize {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String frameSize;

    @OneToMany
    List<Bike> bike;
}
