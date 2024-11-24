package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.*;
import com.bikeparadise.bikewebapp.model.*;
import com.bikeparadise.bikewebapp.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class BikeService {
    @PersistenceContext
    private EntityManager entityManager;

    private final BikeRepository bikeRepository;
    private final BikeParameterTypeRepository bikeParameterTypeRepository;
    private final BikeAttributeRepository bikeAttributeRepository;
    private final ShopAssistantRepository shopAssistantRepository;
    private final PartTypeRepository partTypeRepository;
    private final PartRepository partRepository;
    private final BikeIdentificationAvailableRepository bikeIdentificationAvailableRepository;

    public BikeService(BikeRepository bikeRepository, BikeParameterTypeRepository bikeParameterTypeRepository,
                       BikeAttributeRepository bikeAttributeRepository, ShopAssistantRepository shopAssistantRepository,
                       PartRepository partRepository, PartTypeRepository partTypeRepository, BikeIdentificationAvailableRepository bikeIdentificationAvailableRepository) {
        this.bikeRepository = bikeRepository;
        this.bikeParameterTypeRepository = bikeParameterTypeRepository;
        this.bikeAttributeRepository = bikeAttributeRepository;
        this.shopAssistantRepository = shopAssistantRepository;
        this.partRepository = partRepository;
        this.partTypeRepository = partTypeRepository;
        this.bikeIdentificationAvailableRepository = bikeIdentificationAvailableRepository;
    }

    public List<BikeShopDto> getBikes() {
        List<BikeShopDto> bikeShopDtoList = new ArrayList<>();
        List<Bike> bikeList = bikeRepository.findAll();
        for (Bike bike :
                bikeList) {

            //get drive parameters
            List<Part> frontDerailleur = partRepository.findAllByBike_Id_AndPartParameterAttribute_PartType_Type(bike.getId(), "Front Derailleur");
            List<Part> rearDerailleur = partRepository.findAllByBike_Id_AndPartParameterAttribute_PartType_Type(bike.getId(), "Rear Derailleur");

            String drive = "";

            if (frontDerailleur.size() == 0 && rearDerailleur.size() == 0) {
                drive = "1x1";
            } else if (frontDerailleur.size() == 0) {
                String rearNumberOfGears = "";

                if (rearDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().equals("rows")) {
                    rearNumberOfGears = rearDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().replaceAll("[^0-9]", "");
                }

                drive = "1x" + rearNumberOfGears;
            } else if (rearDerailleur.size() == 0) {
                String frontNumberOfGears = "";

                if (frontDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().equals("rows")) {
                    frontNumberOfGears = frontDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().replaceAll("[^0-9]", "");
                }

                drive = frontNumberOfGears + "x1";
            } else {
                String rearNumberOfGears = "";

                if (rearDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().contains("rows")) {
                    rearNumberOfGears = rearDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().replaceAll("[^0-9]", "");
                }

                String frontNumberOfGears = "";
                if (frontDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().contains("rows")) {
                    frontNumberOfGears = frontDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().replaceAll("[^0-9]", "");
                }

                drive = frontNumberOfGears + "x" + rearNumberOfGears;
            }

            //get bike type
            String type = "";
            //get bike make
            String make = "";
            for (BikeAttribute bikeAttribute :
                    bike.getBikeAttribute()) {
                if (bikeAttribute.getBikeParameterType().getType().equals("Type")) {
                    type = bikeAttribute.getBikeParameterAttribute().getAttribute();
                }
                if (bikeAttribute.getBikeParameterType().getType().equals("Make")) {
                    make = bikeAttribute.getBikeParameterAttribute().getAttribute();
                }
            }

            BikeShopDto bikeShopDto = new BikeShopDto(bike.getId(), make, bike.getModelName(), type, drive, bike.getPrice(), bike.getBikeIdentificationAvailable().size());
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
            List<String> list = new ArrayList<>();
            for(BikeParameterAttribute bikeParameterAttribute : bikeParameterType.getBikeParameterAttribute()){
                list.add(bikeParameterAttribute.getAttribute());
            }
            filters.put(bikeParameterType.getType(), list);
        }

        //parts
        List<PartType> partTypeList = partTypeRepository.findAll();
        for(PartType partType : partTypeList){
            List<String> values = new ArrayList<>();
            values.add("None");
            for(PartParameterAttribute partParameterAttribute : partType.getPartParameterAttribute()){
                for(Part part : partParameterAttribute.getPart()){
                    values.add(part.getMake() + " " + part.getModelName());
                }
            }
            filters.put(partType.getType(), values);
        }

        return filters;

    }

    public Map<String, List<String>> getShopFilters(){
        Map<String, List<String>> filters = new HashMap<>();
        List<BikeParameterType> bikeParameterTypeList = bikeParameterTypeRepository.findAll();

        for (BikeParameterType bikeParameterType:
                bikeParameterTypeList) {
            List<String> list = new ArrayList<>();
            for(BikeParameterAttribute bikeParameterAttribute : bikeParameterType.getBikeParameterAttribute()){
                list.add(bikeParameterAttribute.getAttribute());
            }
            filters.put(bikeParameterType.getType(), list);
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
            for (BikeAttribute bikeAttribute : bike.getBikeAttribute()) {
                parts.put(bikeAttribute.getBikeParameterType().getType(), bikeAttribute.getBikeParameterAttribute().getAttribute());
            }

            //part attributes
            for (Part part : bikeParts) {
                parts.put(part.getPartParameterAttribute().getPartType().getType(), part.getMake() + " " + part.getModelName() + ", " + part.getPartParameterAttribute().getPartAttribute().toString());
            }

            List<Review> reviews = bike.getReview();
            List<ReviewPrintDto> reviewPrintDtos = new ArrayList<>();
            for (Review review : reviews) {
                ReviewPrintDto reviewPrintDto = new ReviewPrintDto(review.getId(), review.getClient().getUserData().getFirstName(), review.getClient().getUserData().getLastName(), review.getNumberOfStars(), review.getDescription());
                reviewPrintDtos.add(reviewPrintDto);
            }

            //get make
            String make = "";
            for(BikeAttribute bikeAttribute : bike.getBikeAttribute()){
                if(bikeAttribute.getBikeParameterType().getType().equals("Make")){
                    make = bikeAttribute.getBikeParameterAttribute().getAttribute();
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
        return bikeRepository.findBikeByPart_PartParameterAttribute_PartType_PartAttribute_Attribute(parameter);
    }

    public ResponseEntity<String> addBike(BikeAddDto bikeAddDto) {
        Optional<ShopAssistant> shopAssistant = shopAssistantRepository.findById(bikeAddDto.getShopAssistantId());

        if (shopAssistant.isPresent()) {
            Bike bike = new Bike(bikeAddDto.getModelName(), bikeAddDto.getPrice(), bikeAddDto.getDescription(), shopAssistant.get());
            String[] words = bikeAddDto.getBikeIdentificationsAvailable().split(" ");
            List<BikeIdentificationAvailable> bikeIdsAvailable = new ArrayList<>();
            for(String word : words){
                BikeIdentificationAvailable bikeIdentificationAvailable = new BikeIdentificationAvailable(word);
                bikeIdentificationAvailableRepository.save(bikeIdentificationAvailable);
                bikeIdentificationAvailable.setBike(bike);
                bikeIdsAvailable.add(bikeIdentificationAvailable);
            }
            bike.setBikeIdentificationAvailable(bikeIdsAvailable);
            List<Part> partsOfBike = new ArrayList<>();
            List<PartAttribute> partAttributes = new ArrayList<>();

            List<BikeAttribute> bikeAttributes = new ArrayList<>();

            //retrieve make & model name
            for(BikeAddFiltersDto bikeAddFiltersDto : bikeAddDto.getParts()){

                //if part exist in bike
                if(bikeAddFiltersDto.getAttribute().equals("None")){

                }

                else if(bikeAddFiltersDto.getParameter().equals("Type") || bikeAddFiltersDto.getParameter().equals("Frame size") || bikeAddFiltersDto.getParameter().equals("Make")){
                    BikeAttribute bikeAttribute = bikeAttributeRepository.findBikeAttributeByBikeParameterType_TypeAndBikeParameterAttribute_Attribute(bikeAddFiltersDto.getParameter(), bikeAddFiltersDto.getAttribute());
                    bikeAttributes.add(bikeAttribute);
                }

                else{
                    int spaceIndex = bikeAddFiltersDto.getAttribute().indexOf(" ");

                    if(spaceIndex != -1) {
                        String firstPart = bikeAddFiltersDto.getAttribute().substring(0, spaceIndex);
                        String secondPart = bikeAddFiltersDto.getAttribute().substring(spaceIndex + 1);

                        String make = firstPart;
                        String modelName = secondPart;

                        Part part = partRepository.findPartByMakeAndModelNameAndPartParameterAttribute_PartType_Type(make, modelName, bikeAddFiltersDto.getParameter());
                        List<Bike> list = part.getBike();
                        list.add(bike);
                        part.setBike(list);
                        partsOfBike.add(part);
                    }
                }
            }

            bike.setPart(partsOfBike);
            bike.setBikeAttribute(bikeAttributes);
            bikeRepository.save(bike);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> deleteBike(Integer id) {
        Optional<Bike> bike = bikeRepository.findById(id);
        if (bike.isPresent()) {
            //if someone has bought this bike, you cannot remove it
            if(bike.get().getBikeIdentificationReserved().size() != 0){
                return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
            }
            bikeIdentificationAvailableRepository.deleteAll(bike.get().getBikeIdentificationAvailable());
            bikeRepository.delete(bike.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
