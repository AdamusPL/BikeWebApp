package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.model.Bike;
import com.bikeparadise.bikewebapp.repository.BikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeService {
    private final BikeRepository bikeRepository;

    public BikeService(BikeRepository bikeRepository){
        this.bikeRepository = bikeRepository;
    }

    public List<Bike> getBikes(){
        return bikeRepository.findAll();
    }
}
