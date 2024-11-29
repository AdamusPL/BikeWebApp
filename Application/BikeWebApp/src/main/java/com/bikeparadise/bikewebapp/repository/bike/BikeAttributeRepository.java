package com.bikeparadise.bikewebapp.repository.bike;

import com.bikeparadise.bikewebapp.model.bike.BikeAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeAttributeRepository extends JpaRepository<BikeAttribute, Integer> {
    BikeAttribute findBikeAttributeByBikeParameterType_TypeAndBikeParameterAttribute_Attribute(String type, String attribute);
}
