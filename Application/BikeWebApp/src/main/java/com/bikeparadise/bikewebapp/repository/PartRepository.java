package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
    List<Part> findAllByBike_Id_AndPartParameterAttribute_PartType_Type(Integer id, String type);
    Part findPartByMakeAndModelNameAndPartParameterAttribute_PartType_Type(String make, String modelName, String type);
    List<Part> findPartByPartParameterAttribute_PartType_TypeIn(List<String> types);
}
