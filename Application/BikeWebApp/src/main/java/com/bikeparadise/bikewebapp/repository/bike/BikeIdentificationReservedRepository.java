package com.bikeparadise.bikewebapp.repository.bike;

import com.bikeparadise.bikewebapp.model.bike.BikeIdentificationReserved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeIdentificationReservedRepository extends JpaRepository<BikeIdentificationReserved, Integer> {
}
