package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.BikeIdentificationReserved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeIdentificationReservedRepository extends JpaRepository<BikeIdentificationReserved, Integer> {
}
