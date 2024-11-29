package com.bikeparadise.bikewebapp.repository.bike;

import com.bikeparadise.bikewebapp.model.bike.BikeIdentificationAvailable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeIdentificationAvailableRepository extends JpaRepository<BikeIdentificationAvailable, Integer> {
    BikeIdentificationAvailable findFirstByBike_Id(Integer id);
}
