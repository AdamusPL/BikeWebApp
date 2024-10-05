package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.Bike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
    List<Bike> findBikeByBikeFrameSize_FrameSize(String frameSize);
    List<Bike> findBikeByBikeType_Type(String type);
    List<Bike> findBikeByPriceBetween(Double lowerRange, Double upperRange);
    List<Bike> findBikeByMake(String make);
    List<Bike> findBikeByPart_PartType_PartAttribute_Attribute(String parameter);
}
