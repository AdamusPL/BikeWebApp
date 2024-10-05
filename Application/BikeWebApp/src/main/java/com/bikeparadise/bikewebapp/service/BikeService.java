package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.BikeDetailedInfo;
import com.bikeparadise.bikewebapp.model.Bike;
import com.bikeparadise.bikewebapp.repository.BikeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {
    @PersistenceContext
    private EntityManager entityManager;

    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository){
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> getBikes(){
        return bikeRepository.findAll();
    }

    public BikeDetailedInfo getDetailedInfoAboutBike(int id){
        return (BikeDetailedInfo) entityManager
                .createNamedStoredProcedureQuery("DetailedInfoAboutBike")
                .setParameter("BikeId", id)
                .getSingleResult();
    }

    public List<Bike> getBikeByFrameSize(String frameSize){
        return bikeRepository.findBikeByBikeFrameSize_FrameSize(frameSize);
    }

    public List<Bike> getBikeByType(String type){
        return bikeRepository.findBikeByBikeType_Type(type);
    }

    public List<Bike> getBikeByPrice(Double lowerRange, Double upperRange){
        return bikeRepository.findBikeByPriceBetween(lowerRange, upperRange);
    }

    public List<Bike> getBikeByMake(String make){
        return bikeRepository.findBikeByMake(make);
    }

    public List<Bike> getBikeByPartAttribute(String parameter){
        return bikeRepository.findBikeByPart_PartType_PartAttribute_Attribute(parameter);
    }

    public ResponseEntity<String> addBike(Bike bike){
        bikeRepository.save(bike);
        return ResponseEntity.ok().build();
    }
}
