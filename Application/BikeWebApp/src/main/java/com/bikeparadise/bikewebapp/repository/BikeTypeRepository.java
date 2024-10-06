package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.BikeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeTypeRepository extends JpaRepository<BikeType, Integer> {
}
