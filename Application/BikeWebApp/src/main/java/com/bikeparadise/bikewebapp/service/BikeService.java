package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.BikeDetailedInfo;
import com.bikeparadise.bikewebapp.model.Bike;
import com.bikeparadise.bikewebapp.repository.BikeRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
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
}
