package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.BikeDetailedInfoDto;
import com.bikeparadise.bikewebapp.dto.BikeAddDto;
import com.bikeparadise.bikewebapp.dto.BikeShopDto;
import com.bikeparadise.bikewebapp.dto.ReviewPrintDto;
import com.bikeparadise.bikewebapp.model.*;
import com.bikeparadise.bikewebapp.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BikeService {
    @PersistenceContext
    private EntityManager entityManager;

    private final BikeRepository bikeRepository;
    private final BikeParameterTypeRepository bikeParameterTypeRepository;
    private final BikeParameterAttributeRepository bikeParameterAttributeRepository;
    private final ShopAssistantRepository shopAssistantRepository;
    private final PartTypeRepository partTypeRepository;
    private final PartRepository partRepository;

    public BikeService(BikeRepository bikeRepository, BikeParameterTypeRepository bikeParameterTypeRepository,
                       BikeParameterAttributeRepository bikeParameterAttributeRepository, ShopAssistantRepository shopAssistantRepository,
                       PartRepository partRepository, PartTypeRepository partTypeRepository) {
        this.bikeRepository = bikeRepository;
        this.bikeParameterTypeRepository = bikeParameterTypeRepository;
        this.bikeParameterAttributeRepository = bikeParameterAttributeRepository;
        this.shopAssistantRepository = shopAssistantRepository;
        this.partRepository = partRepository;
        this.partTypeRepository = partTypeRepository;
    }

    public List<BikeShopDto> getBikes() {
        List<BikeShopDto> bikeShopDtoList = new ArrayList<>();
        List<Bike> bikeList = bikeRepository.findAll();
        for (Bike bike :
                bikeList) {

            //get drive parameters
            List<Part> frontDerailleur = partRepository.findAllByBike_Id_AndPartType_Type(bike.getId(), "Front Derailleur");
            List<Part> rearDerailleur = partRepository.findAllByBike_Id_AndPartType_Type(bike.getId(), "Rear Derailleur");

            String drive = "";

            if (frontDerailleur.size() == 0 && rearDerailleur.size() == 0) {
                drive = "1x1";
            } else if (frontDerailleur.size() == 0) {
                String rearNumberOfGears = "";

                if (rearDerailleur.get(0).getPartType().getPartAttribute().getAttribute().contains("rows")) {
                    rearNumberOfGears = rearDerailleur.get(0).getPartType().getPartAttribute().getAttribute().replaceAll("[^0-9]", "");
                }

                drive = "1x" + rearNumberOfGears;
            } else if (rearDerailleur.size() == 0) {
                String frontNumberOfGears = "";

                if (frontDerailleur.get(0).getPartType().getPartAttribute().getAttribute().contains("rows")) {
                    frontNumberOfGears = frontDerailleur.get(0).getPartType().getPartAttribute().getAttribute().replaceAll("[^0-9]", "");
                }

                drive = frontNumberOfGears + "x1";
            } else {
                String rearNumberOfGears = "";

                if (rearDerailleur.get(0).getPartType().getPartAttribute().getAttribute().contains("rows")) {
                    rearNumberOfGears = rearDerailleur.get(0).getPartType().getPartAttribute().getAttribute().replaceAll("[^0-9]", "");
                }

                String frontNumberOfGears = "";
                if (frontDerailleur.get(0).getPartType().getPartAttribute().getAttribute().contains("rows")) {
                    frontNumberOfGears = frontDerailleur.get(0).getPartType().getPartAttribute().getAttribute().replaceAll("[^0-9]", "");
                }

                drive = frontNumberOfGears + "x" + rearNumberOfGears;
            }

            //get bike type
            String type = "";
            //get bike make
            String make = "";
            for (BikeParameterType bikeParameterType :
                    bike.getBikeParameterType()) {
                if (bikeParameterType.getType().equals("Type")) {
                    type = bikeParameterType.getBikeParameterAttribute().getAttribute();
                }
                if (bikeParameterType.getType().equals("Make")) {
                    make = bikeParameterType.getBikeParameterAttribute().getAttribute();
                }
            }

            BikeShopDto bikeShopDto = new BikeShopDto(bike.getId(), make + " " + bike.getModelName(), type, drive, bike.getPrice(), bike.getBikeIdentificationAvailable().size());
            bikeShopDtoList.add(bikeShopDto);
        }
        return bikeShopDtoList;
    }

    public Map<String, List<String>> getAddBikeFilters(){
        Map<String, List<String>> filters = new HashMap<>();
        List<BikeParameterType> bikeParameterTypeList = bikeParameterTypeRepository.findAll();

        //bike parameters
        for (BikeParameterType bikeParameterType:
                bikeParameterTypeList) {
            boolean isAdded = false;
            for (String key : filters.keySet()) {
                if(key.equals(bikeParameterType.getType())){
                    filters.get(key).add(bikeParameterType.getBikeParameterAttribute().getAttribute());
                    isAdded = true;
                    break;
                }
            }

            if(!isAdded){
                List<String> list = new ArrayList<>();
                list.add(bikeParameterType.getBikeParameterAttribute().getAttribute());
                filters.put(bikeParameterType.getType(), list);
            }
        }

        //parts
        List<PartType> partTypeList = partTypeRepository.findAll();
        for(PartType partType : partTypeList){
            List<String> models = new ArrayList<>();
            models.add("None");
            for(Part part : partType.getPart()){
                models.add(part.getMake() + " " + part.getModelName());
            }
            filters.put(partType.getType(), models);
        }

        return filters;

    }

    public Map<String, List<String>> getShopFilters(){
        Map<String, List<String>> filters = new HashMap<>();
        List<BikeParameterType> bikeParameterTypeList = bikeParameterTypeRepository.findAll();

        for (BikeParameterType bikeParameterType:
             bikeParameterTypeList) {
            boolean isAdded = false;
            for (String key : filters.keySet()) {
                if(key.equals(bikeParameterType.getType())){
                    filters.get(key).add(bikeParameterType.getBikeParameterAttribute().getAttribute());
                    isAdded = true;
                    break;
                }
            }

            if(!isAdded){
                List<String> list = new ArrayList<>();
                list.add(bikeParameterType.getBikeParameterAttribute().getAttribute());
                filters.put(bikeParameterType.getType(), list);
            }
        }

        return filters;

    }

    public BikeDetailedInfoDto getDetailedInfoAboutBike(int id) {
        Optional<Bike> bikeOptional = bikeRepository.findById(id);
        if (bikeOptional.isPresent()) {
            Bike bike = bikeOptional.get();
            List<Part> bikeParts = bike.getPart();
            Map<String, String> parts = new HashMap<>();

            //bike attributes
            for (BikeParameterType bikeParameterType : bike.getBikeParameterType()) {
                parts.put(bikeParameterType.getType(), bikeParameterType.getBikeParameterAttribute().getAttribute());
            }

            //part attributes
            for (Part part : bikeParts) {
                parts.put(part.getPartType().getType(), part.getMake() + " " + part.getModelName() + ", " + part.getPartAttribute().toString());
            }

            List<Review> reviews = bike.getReview();
            List<ReviewPrintDto> reviewPrintDtos = new ArrayList<>();
            for (Review review : reviews) {
                ReviewPrintDto reviewPrintDto = new ReviewPrintDto(review.getClient().getUserData().getFirstName(), review.getClient().getUserData().getLastName(), review.getNumberOfStars(), review.getDescription());
                reviewPrintDtos.add(reviewPrintDto);
            }

            //get make
            String make = "";
            for(BikeParameterType bikeParameterType : bike.getBikeParameterType()){
                if(bikeParameterType.getType().equals("Make")){
                    make = bikeParameterType.getBikeParameterAttribute().getAttribute();
                }
            }


            BikeDetailedInfoDto bikeDetailedInfoDto = new BikeDetailedInfoDto(bike.getId(), make + " " + bike.getModelName(), bike.getBikeIdentificationAvailable().size(), bike.getPrice(), bike.getDescription(), parts, reviewPrintDtos);
            return bikeDetailedInfoDto;
        }

        return null;
    }

//    public List<Bike> getBikeByFrameSize(String frameSize) {
//        return bikeRepository.findBikeByBikeFrameSize_FrameSize(frameSize);
//    }
//
//    public List<Bike> getBikeByType(String type) {
//        return bikeRepository.findBikeByBikeType_Type(type);
//    }

    public List<Bike> getBikeByPrice(Double lowerRange, Double upperRange) {
        return bikeRepository.findBikeByPriceBetween(lowerRange, upperRange);
    }

//    public List<Bike> getBikeByMake(String make) {
//        return bikeRepository.findBikeByMake(make);
//    }

    public List<Bike> getBikeByPartAttribute(String parameter) {
        return bikeRepository.findBikeByPart_PartType_PartAttribute_Attribute(parameter);
    }

    public ResponseEntity<String> addBike(BikeAddDto bikeAddDto) {
        Optional<ShopAssistant> shopAssistant = shopAssistantRepository.findById(bikeAddDto.getShopAssistantId());

        if (shopAssistant.isPresent()) {
            Bike bike = new Bike(bikeAddDto.getModelName(), bikeAddDto.getPrice(), bikeAddDto.getDescription(), shopAssistant.get());
            bikeRepository.save(bike);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> deleteBike(Integer id) {
        Optional<Bike> bike = bikeRepository.findById(id);
        if (bike.isPresent()) {
            bikeRepository.delete(bike.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
