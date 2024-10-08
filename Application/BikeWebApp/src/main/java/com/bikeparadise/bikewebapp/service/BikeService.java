package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.BikeDetailedInfoDto;
import com.bikeparadise.bikewebapp.dto.BikeDto;
import com.bikeparadise.bikewebapp.model.*;
import com.bikeparadise.bikewebapp.repository.BikeFrameSizeRepository;
import com.bikeparadise.bikewebapp.repository.BikeRepository;
import com.bikeparadise.bikewebapp.repository.BikeTypeRepository;
import com.bikeparadise.bikewebapp.repository.ShopAssistantRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class BikeService {
    @PersistenceContext
    private EntityManager entityManager;

    private final BikeRepository bikeRepository;
    private final BikeTypeRepository bikeTypeRepository;
    private final BikeFrameSizeRepository bikeFrameSizeRepository;
    private final ShopAssistantRepository shopAssistantRepository;

    public BikeService(BikeRepository bikeRepository, BikeTypeRepository bikeTypeRepository, BikeFrameSizeRepository bikeFrameSizeRepository, ShopAssistantRepository shopAssistantRepository) {
        this.bikeRepository = bikeRepository;
        this.bikeTypeRepository = bikeTypeRepository;
        this.bikeFrameSizeRepository = bikeFrameSizeRepository;
        this.shopAssistantRepository = shopAssistantRepository;
    }

    public List<Bike> getBikes() {
        return bikeRepository.findAll();
    }

    public BikeDetailedInfoDto getDetailedInfoAboutBike(int id) {
        Optional<Bike> bikeOptional = bikeRepository.findById(id);
        if(bikeOptional.isPresent()){
            Bike bike = bikeOptional.get();
            List<Part> bikeParts = bike.getPart();
            Map<String, String> parts = new HashMap<>();

            for(Part part : bikeParts){
                parts.put(part.getPartType().getType(), part.getMake() + " " + part.getModelName() + ", " + part.getPartAttribute().toString());
            }

            BikeDetailedInfoDto bikeDetailedInfoDto = new BikeDetailedInfoDto(bike.getMake(), bike.getModelName(), bike.getBikeType().getType(), bike.getPrice(), bike.getBikeFrameSize().getFrameSize(), parts);
            return bikeDetailedInfoDto;
        }

        return null;
    }

    public List<Bike> getBikeByFrameSize(String frameSize) {
        return bikeRepository.findBikeByBikeFrameSize_FrameSize(frameSize);
    }

    public List<Bike> getBikeByType(String type) {
        return bikeRepository.findBikeByBikeType_Type(type);
    }

    public List<Bike> getBikeByPrice(Double lowerRange, Double upperRange) {
        return bikeRepository.findBikeByPriceBetween(lowerRange, upperRange);
    }

    public List<Bike> getBikeByMake(String make) {
        return bikeRepository.findBikeByMake(make);
    }

    public List<Bike> getBikeByPartAttribute(String parameter) {
        return bikeRepository.findBikeByPart_PartType_PartAttribute_Attribute(parameter);
    }

    public ResponseEntity<String> addBike(BikeDto bikeDto) {
        Optional<BikeType> bikeType = bikeTypeRepository.findById(bikeDto.getBikeTypeId());
        Optional<BikeFrameSize> bikeFrameSize = bikeFrameSizeRepository.findById(bikeDto.getBikeFrameSizeId());
        Optional<ShopAssistant> shopAssistant = shopAssistantRepository.findById(bikeDto.getShopAssistantId());

        if (bikeType.isPresent() && bikeFrameSize.isPresent() && shopAssistant.isPresent()) {
            Bike bike = new Bike(bikeDto.getMake(), bikeDto.getModelName(), bikeDto.getPrice(), bikeDto.getDescription(), bikeType.get(), bikeFrameSize.get(), shopAssistant.get());
            bikeRepository.save(bike);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> deleteBike(Integer id){
        Optional<Bike> bike = bikeRepository.findById(id);
        if(bike.isPresent()){
            bikeRepository.delete(bike.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
