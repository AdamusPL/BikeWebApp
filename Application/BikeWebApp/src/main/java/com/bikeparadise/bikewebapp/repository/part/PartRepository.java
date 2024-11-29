package com.bikeparadise.bikewebapp.repository.part;

import com.bikeparadise.bikewebapp.model.part.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
    List<Part> findAllByBike_Id_AndPartParameterAttribute_PartType_Type(Integer id, String type);
    Part findPartByMakeAndModelNameAndPartParameterAttribute_PartType_Type(String make, String modelName, String type);
    List<Part> findPartByPartParameterAttribute_PartType_TypeInAndPriceBetween(List<String> types, BigDecimal minPrice, BigDecimal maxPrice);
    List<Part> findPartByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
    @Query("SELECT MAX(p.price) FROM Part p")
    BigDecimal findMaxPrice();
    @Query("SELECT MIN(p.price) FROM Part p")
    BigDecimal findMinPrice();
}
