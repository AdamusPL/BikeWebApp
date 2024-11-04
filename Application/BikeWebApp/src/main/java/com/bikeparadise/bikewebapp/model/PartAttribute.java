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
public class PartAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String attribute;

    @Override
    public String toString() {
        return attribute;
    }

    @OneToMany
    List<PartType> partType;

    @ManyToMany
    @JoinTable(
            name = "Part_PartAttribute",
            joinColumns = @JoinColumn(name = "PartAttributeId"),
            inverseJoinColumns = @JoinColumn(name = "PartId"))
    private List<Part> part;

}
