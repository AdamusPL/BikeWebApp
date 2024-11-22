package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PartParameterAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "PartParameterAttributeId")
    private List<Part> part;

    @ManyToOne
    private PartType partType;

    @ManyToOne
    private PartAttribute partAttribute;

    public PartParameterAttribute(PartType partType, PartAttribute partAttribute){
        this.partType = partType;
        this.partAttribute = partAttribute;
    }
}
