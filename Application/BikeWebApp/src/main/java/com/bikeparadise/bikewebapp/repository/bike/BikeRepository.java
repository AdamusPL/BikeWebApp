package com.bikeparadise.bikewebapp.repository.bike;

import com.bikeparadise.bikewebapp.model.bike.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
    List<Bike> findBikeByPriceBetween(Double lowerRange, Double upperRange);
    List<Bike> findBikeByPart_PartParameterAttribute_PartType_PartAttribute_Attribute(String parameter);
    List<Bike> findByBikeAttribute_BikeParameterAttribute_AttributeIn(List<String> attributes);
    @Query("SELECT MAX(b.price) FROM Bike b")
    BigDecimal findMaxPrice();
    @Query("SELECT MIN(b.price) FROM Bike b")
    BigDecimal findMinPrice();
}
