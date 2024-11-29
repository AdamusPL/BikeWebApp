package com.bikeparadise.bikewebapp.repository.bike;

import com.bikeparadise.bikewebapp.model.bike.BikeParameterType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeParameterTypeRepository extends JpaRepository<BikeParameterType, Integer> {
}
