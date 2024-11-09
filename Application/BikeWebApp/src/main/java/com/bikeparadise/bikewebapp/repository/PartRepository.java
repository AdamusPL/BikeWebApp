package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
    List<Part> findAllByBike_Id_AndPartType_Type(Integer id, String type);
    Part findPartByMakeAndModelName(String make, String modelName);
}
