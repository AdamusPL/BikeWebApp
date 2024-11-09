package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.BikeParameterType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BikeParameterTypeRepository extends JpaRepository<BikeParameterType, Integer> {
    BikeParameterType findBikeParameterTypeByTypeAndBikeParameterAttribute_Attribute(String type, String attribute);
}
