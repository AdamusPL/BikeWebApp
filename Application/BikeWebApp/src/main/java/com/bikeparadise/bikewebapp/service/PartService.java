package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.PartDetailedInfoDto;
import com.bikeparadise.bikewebapp.dto.PartDto;
import com.bikeparadise.bikewebapp.dto.PartShopDto;
import com.bikeparadise.bikewebapp.dto.ReviewPrintDto;
import com.bikeparadise.bikewebapp.model.*;
import com.bikeparadise.bikewebapp.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PartService {
    private final PartRepository partRepository;
    private final PartTypeRepository partTypeRepository;
    private final ShopAssistantRepository shopAssistantRepository;
    private final PartAttributeRepository partAttributeRepository;
    private final PartParameterAttributeRepository partParameterAttributeRepository;

    public PartService(PartRepository partRepository, PartTypeRepository partTypeRepository, ShopAssistantRepository shopAssistantRepository,
                       PartAttributeRepository partAttributeRepository, PartParameterAttributeRepository partParameterAttributeRepository) {
        this.partRepository = partRepository;
        this.partTypeRepository = partTypeRepository;
        this.shopAssistantRepository = shopAssistantRepository;
        this.partAttributeRepository = partAttributeRepository;
        this.partParameterAttributeRepository = partParameterAttributeRepository;
    }

    public List<PartShopDto> getParts() {
        List<PartShopDto> partShopDtoList = new ArrayList<>();
        List<Part> partList = partRepository.findAll();

        for (Part part :
                partList) {

            PartShopDto partShopDto = new PartShopDto(part.getId(), part.getMake(), part.getModelName(), part.getPartParameterAttribute().getPartType().getType(), part.getPartParameterAttribute().getPartAttribute().getAttribute(), part.getPrice(), part.getQuantityInStock());
            partShopDtoList.add(partShopDto);
        }

        return partShopDtoList;
    }

    public PartDetailedInfoDto getDetailedInfoAboutPart(int id) {
        Optional<Part> partOptional = partRepository.findById(id);

        if (partOptional.isPresent()) {
            Part part = partOptional.get();

            List<Review> reviews = part.getReview();
            List<ReviewPrintDto> reviewPrintDtos = new ArrayList<>();
            for (Review review : reviews) {
                ReviewPrintDto reviewPrintDto = new ReviewPrintDto(review.getId(), review.getClient().getUserData().getFirstName(), review.getClient().getUserData().getLastName(), review.getNumberOfStars(), review.getDescription());
                reviewPrintDtos.add(reviewPrintDto);
            }

            PartDetailedInfoDto partDetailedInfoDto = new PartDetailedInfoDto(part.getId(), part.getMake() + " " + part.getModelName(), part.getPrice(), part.getQuantityInStock(), part.getDescription(), part.getPartParameterAttribute().getPartType().getType(), part.getPartParameterAttribute().getPartAttribute().getAttribute(), reviewPrintDtos);
            return partDetailedInfoDto;
        }
        return null;
    }

    public Map<String, List<String>> getFilters() {
        Map<String, List<String>> filters = new HashMap<>();
        List<PartType> partTypeList = partTypeRepository.findAll();

        for (PartType partType : partTypeList) {
            String type = partType.getType();
            List<String> attributes = new ArrayList<>();
            for(PartAttribute partAttribute : partType.getPartAttribute()){
                attributes.add(partAttribute.getAttribute());
            }
            filters.put(type, attributes);
        }

        return filters;
    }

    public ResponseEntity<String> addPart(PartDto partDto) {
        Optional<ShopAssistant> shopAssistant = shopAssistantRepository.findById(partDto.getShopAssistantId());
        PartType partType = partTypeRepository.findFirstByType(partDto.getType());
        PartAttribute partAttribute = partAttributeRepository.findFirstByAttribute(partDto.getAttribute());

        if (shopAssistant.isPresent()) {
            PartParameterAttribute partParameterAttribute = new PartParameterAttribute(partType, partAttribute);
            Part part = new Part(partDto.getMake(), partDto.getModelName(), partDto.getPrice(), partDto.getQuantityInStock(), partDto.getDescription(), partParameterAttribute, shopAssistant.get());
            partParameterAttribute.setPart(new ArrayList<>(List.of(part)));
            part.setPartParameterAttribute(partParameterAttribute);
            partRepository.save(part);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> deletePart(Integer id) {
        Optional<Part> part = partRepository.findById(id);
        if (part.isPresent()) {
            partRepository.delete(part.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
