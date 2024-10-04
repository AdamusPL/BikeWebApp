package com.bikeparadise.bikewebapp.dto;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@NamedStoredProcedureQuery(name = "DetailedInfoAboutBike", procedureName = "DetailedInfoAboutBike",
        resultClasses= BikeDetailedInfo.class,
        parameters = {
                @StoredProcedureParameter(mode = ParameterMode.IN, name = "BikeId", type = Integer.class)
        })
@AllArgsConstructor
@NoArgsConstructor
public class BikeDetailedInfo {
    String make;
    String modelName;
    String type;
    Double price;
    Integer quantityInStock;
    String frameSize;
    String frontDerailleur;
    String rearDerailleur;
    String tyres;
    String brakes;
    String cassette;
    String frontShifter;
    String rearShifter;
    String crankset;
    String chain;
}
