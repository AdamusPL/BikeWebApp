package com.bikeparadise.bikewebapp.repository.bike;

import com.bikeparadise.bikewebapp.model.bike.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
    @Query("SELECT MAX(b.price) FROM Bike b")
    BigDecimal findMaxPrice();
    @Query("SELECT MIN(b.price) FROM Bike b")
    BigDecimal findMinPrice();
    List<Bike> findBikeByBikeAttribute_BikeParameterAttribute_AttributeInAndPriceBetween(List<String> attributes, BigDecimal minPrice, BigDecimal maxPrice);
    List<Bike> findBikeByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
