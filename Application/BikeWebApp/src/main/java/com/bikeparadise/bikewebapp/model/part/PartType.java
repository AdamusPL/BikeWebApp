package com.bikeparadise.bikewebapp.model.part;

import com.bikeparadise.bikewebapp.model.part.PartAttribute;
import com.bikeparadise.bikewebapp.model.part.PartParameterAttribute;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PartType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique=true)
    String type;

    @OneToMany(mappedBy = "partType")
    private List<PartAttribute> partAttribute;

    @OneToMany(mappedBy = "partType")
    private List<PartParameterAttribute> partParameterAttribute;
}
