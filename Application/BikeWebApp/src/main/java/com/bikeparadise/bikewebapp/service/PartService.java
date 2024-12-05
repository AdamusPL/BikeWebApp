package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.part.*;
import com.bikeparadise.bikewebapp.dto.review.ReviewPrintDto;
import com.bikeparadise.bikewebapp.model.part.Part;
import com.bikeparadise.bikewebapp.model.part.PartAttribute;
import com.bikeparadise.bikewebapp.model.part.PartParameterAttribute;
import com.bikeparadise.bikewebapp.model.part.PartType;
import com.bikeparadise.bikewebapp.model.review.Review;
import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
import com.bikeparadise.bikewebapp.repository.part.PartAttributeRepository;
import com.bikeparadise.bikewebapp.repository.part.PartRepository;
import com.bikeparadise.bikewebapp.repository.part.PartTypeRepository;
import com.bikeparadise.bikewebapp.repository.roles.ShopAssistantRepository;
import com.bikeparadise.bikewebapp.service.shared.GetReviews;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PartService {
    private final PartRepository partRepository;
    private final PartTypeRepository partTypeRepository;
    private final ShopAssistantRepository shopAssistantRepository;
    private final PartAttributeRepository partAttributeRepository;

    public PartService(PartRepository partRepository, PartTypeRepository partTypeRepository, ShopAssistantRepository shopAssistantRepository,
                       PartAttributeRepository partAttributeRepository) {
        this.partRepository = partRepository;
        this.partTypeRepository = partTypeRepository;
        this.shopAssistantRepository = shopAssistantRepository;
        this.partAttributeRepository = partAttributeRepository;
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

            List<ReviewPrintDto> reviewPrintDtos = GetReviews.getReviews(part.getReview());

            PartDetailedInfoDto partDetailedInfoDto = new PartDetailedInfoDto(part.getId(), part.getMake() + " " + part.getModelName(), part.getPrice(), part.getQuantityInStock(), part.getDescription(), part.getPartParameterAttribute().getPartType().getType(), part.getPartParameterAttribute().getPartAttribute().getAttribute(), reviewPrintDtos);
            return partDetailedInfoDto;
        }
        return null;
    }

    public PartFiltersDto getShopFilters() {
        List<PartTypeFilterDto> filters = new ArrayList<>();
        List<PartType> partTypeList = partTypeRepository.findAll();

        for (PartType partType : partTypeList) {
            String type = partType.getType();
            filters.add(new PartTypeFilterDto(partType.getId(), type, false));
        }

        BigDecimal maxPrice = partRepository.findMaxPrice();
        BigDecimal minPrice = partRepository.findMinPrice();

        PartFiltersDto partFiltersDto = new PartFiltersDto(filters, minPrice.toString(), maxPrice.toString());

        return partFiltersDto;
    }

    public Map<String, List<String>> getAddPartFilters() {
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

    public List<PartShopDto> getFilteredParts(PartFiltersDto partTypeFilterDtos){
        List<Part> parts;
        List<PartShopDto> partShopDtoList = new ArrayList<>();

        if(!partTypeFilterDtos.getMinPrice().matches("^\\d+(\\.\\d{1,2})?$")
                || !partTypeFilterDtos.getMaxPrice().matches("^\\d+(\\.\\d{1,2})?$")){
            parts = partRepository.findAll();
            for(Part part : parts){
                PartShopDto partShopDto = new PartShopDto(part.getId(), part.getMake(), part.getModelName(), part.getPartParameterAttribute().getPartType().getType(), part.getPartParameterAttribute().getPartAttribute().getAttribute(), part.getPrice(), part.getQuantityInStock());
                partShopDtoList.add(partShopDto);
            }
            return partShopDtoList;
        }

        List<String> types = new ArrayList<>();

        int checked = 0;
        for(PartTypeFilterDto partTypeFilterDto : partTypeFilterDtos.getPartTypeFilterDtos()){
            if(partTypeFilterDto.isChecked()){
                checked++;
                types.add(partTypeFilterDto.getType());
            }
        }

        if(checked == 0){
            parts = partRepository.findPartByPriceBetween(new BigDecimal(partTypeFilterDtos.getMinPrice()), new BigDecimal(partTypeFilterDtos.getMaxPrice()));
        }
        else {
            parts = partRepository.findPartByPartParameterAttribute_PartType_TypeInAndPriceBetween(types, new BigDecimal(partTypeFilterDtos.getMinPrice()), new BigDecimal(partTypeFilterDtos.getMaxPrice()));
        }
        for(Part part : parts){
            PartShopDto partShopDto = new PartShopDto(part.getId(), part.getMake(), part.getModelName(), part.getPartParameterAttribute().getPartType().getType(), part.getPartParameterAttribute().getPartAttribute().getAttribute(), part.getPrice(), part.getQuantityInStock());
            partShopDtoList.add(partShopDto);
        }

        return partShopDtoList;

    }

    private ResponseEntity<String> addPartConstraints(PartDto partDto){
        if(partDto.getMake().equals("")){
            return ResponseEntity.badRequest().body("Error: Make field is missing");
        }

        if(partDto.getMake().length() > 23){
            return ResponseEntity.badRequest().body("Error: Make field can have max. 23 characters");
        }

        if(partDto.getModelName().equals("")){
            return ResponseEntity.badRequest().body("Error: Model name field is missing");
        }

        if(partDto.getModelName().length() > 50){
            return ResponseEntity.badRequest().body("Error: Model name field can have max. 50 characters");
        }

        if(partDto.getPrice() == null){
            return ResponseEntity.badRequest().body("Error: Price field is missing");
        }

        if(partDto.getPrice().scale() > 2) {
            return ResponseEntity.badRequest().body("Error: Price can only have 2 places after comma");
        }

        if(partDto.getDescription().length() > 500){
            return ResponseEntity.badRequest().body("Error: Description can have max. 500 characters");
        }

        if(partDto.getQuantityInStock() == 0){
            return ResponseEntity.badRequest().body("Error: Quantity in stock field is missing / can't have 0 value");
        }

        if(partDto.getQuantityInStock().toString().length() > 10){
            return ResponseEntity.badRequest().body("Error: Quantity in stock can have up to 10 digits");
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> addPart(PartDto partDto) {
        ResponseEntity<String> response = addPartConstraints(partDto);
        if(!response.getStatusCode().equals(HttpStatus.OK)){
            return response;
        }

        Optional<ShopAssistant> shopAssistant = shopAssistantRepository.findById(partDto.getShopAssistantId());
        PartType partType = partTypeRepository.findFirstByType(partDto.getType());
        PartAttribute partAttribute = partAttributeRepository.findFirstByAttribute(partDto.getAttribute());

        if (shopAssistant.isPresent()) {
            PartParameterAttribute partParameterAttribute = new PartParameterAttribute(partType, partAttribute);
            Part part = new Part(partDto.getMake(), partDto.getModelName(), partDto.getPrice(), partDto.getQuantityInStock(), partDto.getDescription(), partParameterAttribute, shopAssistant.get());
            partParameterAttribute.setPart(new ArrayList<>(List.of(part)));
            part.setPartParameterAttribute(partParameterAttribute);
            partRepository.save(part);
            return ResponseEntity.ok().body("Part successfully added");
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
