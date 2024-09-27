package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Derailleur {
    @Id
    Integer id;
    String type;
    Integer numberOfRows;

    @OneToOne
    @JoinColumn(name = "PartId")
    Part part;
}
