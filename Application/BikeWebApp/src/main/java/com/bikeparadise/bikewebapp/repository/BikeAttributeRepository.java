package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.BikeAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeAttributeRepository extends JpaRepository<BikeAttribute, Integer> {
    BikeAttribute findBikeAttributeByBikeParameterType_TypeAndBikeParameterAttribute_Attribute(String type, String attribute);
}
