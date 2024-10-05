package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PartAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String attribute;

    @ManyToOne
    @JoinColumn(name = "PartTypeId")
    PartType partType;

}
