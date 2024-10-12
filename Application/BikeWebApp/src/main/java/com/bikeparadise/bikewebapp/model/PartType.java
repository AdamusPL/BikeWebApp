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
public class PartType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String type;

    @ManyToOne
    @JoinColumn(name = "PartAttributeId")
    PartAttribute partAttribute;

    @OneToMany
    private List<Part> part;
}
