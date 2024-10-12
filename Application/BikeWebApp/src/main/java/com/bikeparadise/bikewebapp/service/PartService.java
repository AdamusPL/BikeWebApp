package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.PartDetailedInfoDto;
import com.bikeparadise.bikewebapp.dto.PartDto;
import com.bikeparadise.bikewebapp.dto.PartShopDto;
import com.bikeparadise.bikewebapp.dto.ReviewPrintDto;
import com.bikeparadise.bikewebapp.model.*;
import com.bikeparadise.bikewebapp.repository.PartRepository;
import com.bikeparadise.bikewebapp.repository.PartTypeRepository;
import com.bikeparadise.bikewebapp.repository.ShopAssistantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartService {
    private final PartRepository partRepository;
    private final PartTypeRepository partTypeRepository;
    private final ShopAssistantRepository shopAssistantRepository;

    public PartService(PartRepository partRepository, PartTypeRepository partTypeRepository, ShopAssistantRepository shopAssistantRepository) {
        this.partRepository = partRepository;
        this.partTypeRepository = partTypeRepository;
        this.shopAssistantRepository = shopAssistantRepository;
    }

    public List<PartShopDto> getParts() {
        List<PartShopDto> partShopDtoList = new ArrayList<>();
        List<Part> partList = partRepository.findAll();

        for (Part part:
             partList) {

            Map<String, String> attributesList = new HashMap<>();
            String type = "";
            String make = "";

//            for(PartType partType: part.getPartType()){
//                if(partType.getType().equals("Type")){
//                    type = partType.getPartAttribute().getAttribute();
//                }
//                else if(partType.getType().equals("Make")){
//                    make = partType.getPartAttribute().getAttribute();
//                }
//                else{
//
//                }
//            }

            PartShopDto partShopDto = new PartShopDto(part.getId(), make + " " + part.getModelName(), type, attributesList, part.getPrice());
            partShopDtoList.add(partShopDto);
        }

        return partShopDtoList;
    }

    public PartDetailedInfoDto getDetailedInfoAboutPart(int id){
        Optional<Part> partOptional = partRepository.findById(id);

        if(partOptional.isPresent()){
            Part part = partOptional.get();

            List<String> partAttributes = new ArrayList<>();
            for(PartAttribute partAttribute : part.getPartAttribute()){
                partAttributes.add(partAttribute.getAttribute());
            }

            List<Review> reviews = part.getReview();
            List<ReviewPrintDto> reviewPrintDtos = new ArrayList<>();
            for(Review review : reviews){
                ReviewPrintDto reviewPrintDto = new ReviewPrintDto(review.getClient().getUserData().getFirstName(), review.getClient().getUserData().getLastName(), review.getNumberOfStars(), review.getDescription());
                reviewPrintDtos.add(reviewPrintDto);
            }

            PartDetailedInfoDto partDetailedInfoDto = new PartDetailedInfoDto(part.getMake(), part.getModelName(), part.getPrice(), part.getQuantityInStock(), part.getDescription(), partAttributes, reviewPrintDtos);
            return partDetailedInfoDto;
        }
        return null;
    }

    public ResponseEntity<String> addPart(PartDto partDto) {
        Optional<ShopAssistant> shopAssistant = shopAssistantRepository.findById(partDto.getShopAssistantId());

//        if(partType.isPresent() && shopAssistant.isPresent()){
//            Part part = new Part(partDto.getMake(), partDto.getModelName(), partDto.getPrice(), partDto.getQuantityInStock(), partDto.getDescription(), partType.get(), shopAssistant.get());
//            partRepository.save(part);
//            return ResponseEntity.ok().build();
//        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> deletePart(Integer id){
        Optional<Part> part = partRepository.findById(id);
        if(part.isPresent()){
            partRepository.delete(part.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
