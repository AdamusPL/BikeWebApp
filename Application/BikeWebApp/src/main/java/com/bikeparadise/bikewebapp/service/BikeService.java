package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.bike.*;
import com.bikeparadise.bikewebapp.dto.review.ReviewPrintDto;
import com.bikeparadise.bikewebapp.model.bike.*;
import com.bikeparadise.bikewebapp.model.part.Part;
import com.bikeparadise.bikewebapp.model.part.PartParameterAttribute;
import com.bikeparadise.bikewebapp.model.part.PartType;
import com.bikeparadise.bikewebapp.model.review.Review;
import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
import com.bikeparadise.bikewebapp.repository.bike.*;
import com.bikeparadise.bikewebapp.repository.part.PartRepository;
import com.bikeparadise.bikewebapp.repository.part.PartTypeRepository;
import com.bikeparadise.bikewebapp.repository.roles.ShopAssistantRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class BikeService {
    private final BikeRepository bikeRepository;
    private final BikeParameterTypeRepository bikeParameterTypeRepository;
    private final BikeAttributeRepository bikeAttributeRepository;
    private final ShopAssistantRepository shopAssistantRepository;
    private final PartTypeRepository partTypeRepository;
    private final PartRepository partRepository;
    private final BikeIdentificationAvailableRepository bikeIdentificationAvailableRepository;
    private final BikeIdentificationReservedRepository bikeIdentificationReservedRepository;

    public BikeService(BikeRepository bikeRepository, BikeParameterTypeRepository bikeParameterTypeRepository,
                       BikeAttributeRepository bikeAttributeRepository, ShopAssistantRepository shopAssistantRepository,
                       PartRepository partRepository, PartTypeRepository partTypeRepository,
                       BikeIdentificationAvailableRepository bikeIdentificationAvailableRepository,
                       BikeIdentificationReservedRepository bikeIdentificationReservedRepository) {
        this.bikeRepository = bikeRepository;
        this.bikeParameterTypeRepository = bikeParameterTypeRepository;
        this.bikeAttributeRepository = bikeAttributeRepository;
        this.shopAssistantRepository = shopAssistantRepository;
        this.partRepository = partRepository;
        this.partTypeRepository = partTypeRepository;
        this.bikeIdentificationAvailableRepository = bikeIdentificationAvailableRepository;
        this.bikeIdentificationReservedRepository = bikeIdentificationReservedRepository;
    }

    private String getDrive(Bike bike) {
        //get drive parameters
        List<Part> frontDerailleur = partRepository.findAllByBike_Id_AndPartParameterAttribute_PartType_Type(bike.getId(), "Front Derailleur");
        List<Part> rearDerailleur = partRepository.findAllByBike_Id_AndPartParameterAttribute_PartType_Type(bike.getId(), "Rear Derailleur");

        String drive = "";

        if (frontDerailleur.size() == 0 && rearDerailleur.size() == 0) {
            drive = "1x1";
        } else if (frontDerailleur.size() == 0) {
            String rearNumberOfGears = "";

            if (rearDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().contains("rows")) {
                rearNumberOfGears = rearDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().replaceAll("[^0-9]", "");
            }

            drive = "1x" + rearNumberOfGears;
        } else if (rearDerailleur.size() == 0) {
            String frontNumberOfGears = "";

            if (frontDerailleur.get(0).getPartParameterAttribute().getPartAttribute().getAttribute().contains("rows")) {
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
        return drive;
    }

    private String getMake(Bike bike) {
        //get bike make
        String make = "";

        for (BikeAttribute bikeAttribute :
                bike.getBikeAttribute()) {
            if (bikeAttribute.getBikeParameterType().getType().equals("Make")) {
                make = bikeAttribute.getBikeParameterAttribute().getAttribute();
            }
        }

        return make;
    }

    private String getType(Bike bike) {
        //get bike type
        String type = "";

        for (BikeAttribute bikeAttribute :
                bike.getBikeAttribute()) {
            if (bikeAttribute.getBikeParameterType().getType().equals("Type")) {
                type = bikeAttribute.getBikeParameterAttribute().getAttribute();
            }
        }

        return type;
    }

    public Map<String, List<String>> getAddBikeFilters() {
        Map<String, List<String>> filters = new HashMap<>();
        List<BikeParameterType> bikeParameterTypeList = bikeParameterTypeRepository.findAll();

        //bike parameters
        for (BikeParameterType bikeParameterType :
                bikeParameterTypeList) {
            List<String> list = new ArrayList<>();
            for (BikeParameterAttribute bikeParameterAttribute : bikeParameterType.getBikeParameterAttribute()) {
                list.add(bikeParameterAttribute.getAttribute());
            }
            filters.put(bikeParameterType.getType(), list);
        }

        //parts
        List<PartType> partTypeList = partTypeRepository.findAll();
        for (PartType partType : partTypeList) {
            List<String> values = new ArrayList<>();
            values.add("None");
            for (PartParameterAttribute partParameterAttribute : partType.getPartParameterAttribute()) {
                for (Part part : partParameterAttribute.getPart()) {
                    values.add(part.getMake() + " " + part.getModelName());
                }
            }
            filters.put(partType.getType(), values);
        }

        return filters;

    }

    public BikeFiltersDto getShopFilters() {
        List<BikeFilterCheckboxDto> filters = new ArrayList<>();
        List<BikeParameterType> bikeParameterTypeList = bikeParameterTypeRepository.findAll();

        for (BikeParameterType bikeParameterType :
                bikeParameterTypeList) {
            List<BikeFilterAttributeDto> list = new ArrayList<>();
            for (BikeParameterAttribute bikeParameterAttribute : bikeParameterType.getBikeParameterAttribute()) {
                list.add(new BikeFilterAttributeDto(bikeParameterAttribute.getId(), bikeParameterAttribute.getAttribute(), false));
            }
            filters.add(new BikeFilterCheckboxDto(bikeParameterType.getId(), bikeParameterType.getType(), list));
        }

        //find max and min price
        BigDecimal maxPrice = bikeRepository.findMaxPrice();
        BigDecimal minPrice = bikeRepository.findMinPrice();

        BikeFiltersDto bikeFiltersDto = new BikeFiltersDto(filters, minPrice.toString(), maxPrice.toString());

        return bikeFiltersDto;

    }

    public static List<List<String>> getCombinations(Map<String, List<String>> map) {
        List<String> keys = new ArrayList<>(map.keySet());
        List<List<String>> results = new ArrayList<>();
        generateCombinations(map, keys, 0, new ArrayList<>(), results);
        return results;
    }

    private static void generateCombinations(Map<String, List<String>> map, List<String> keys, int index, List<String> current, List<List<String>> results) {
        if (index == keys.size()) {
            // Base case: all keys are processed, add the current combination to results
            results.add(new ArrayList<>(current));
            return;
        }

        // Recursive case: iterate over the values of the current key
        String key = keys.get(index);
        for (String value : map.get(key)) {
            current.add(value);
            generateCombinations(map, keys, index + 1, current, results);
            current.remove(current.size() - 1); // Backtrack
        }
    }

    public List<BikeShopDto> getFilteredBikes(BikeFiltersDto filters) {
        List<Bike> bikes = new ArrayList<>();
        List<BikeShopDto> bikeShopDtoList = new ArrayList<>();

        if(!filters.getMinPrice().matches("^\\d+(\\.\\d{1,2})?$")
        || !filters.getMaxPrice().matches("^\\d+(\\.\\d{1,2})?$")){
            bikes = bikeRepository.findAll();
            for (Bike bike : bikes) {
                String drive = getDrive(bike);
                String make = getMake(bike);
                String type = getType(bike);

                BikeShopDto bikeShopDto = new BikeShopDto(bike.getId(), make, bike.getModelName(), type, drive, bike.getPrice(), bike.getBikeIdentificationAvailable().size());
                bikeShopDtoList.add(bikeShopDto);
            }
            return bikeShopDtoList;
        }

        Map<String, List<String>> typesAndAttributes = new HashMap<>();
        for (BikeFilterCheckboxDto bikeFilterCheckboxDto : filters.getBikeFilterCheckboxDtos()) {
            String type = bikeFilterCheckboxDto.getType();
            List<String> attributes = new ArrayList<>();
            for (BikeFilterAttributeDto bikeFilterAttributeDto : bikeFilterCheckboxDto.getAttribute()) {
                if (bikeFilterAttributeDto.isChecked()) {
                    attributes.add(bikeFilterAttributeDto.getAttribute());
                }
            }
            if (attributes.size() != 0) {
                typesAndAttributes.put(type, attributes);
            }
        }

        if (typesAndAttributes.size() == 0) {
            bikes = bikeRepository.findBikeByPriceBetween(new BigDecimal(filters.getMinPrice()), new BigDecimal(filters.getMaxPrice()));
        } else {
            List<Bike> bikeCase = new ArrayList<>();

            List<Integer> bikeIds = new ArrayList<>();
            for (Bike bike : bikeCase) {

                boolean skip = false;

                for (Integer id : bikeIds) {
                    if (id.equals(bike.getId())) {
                        skip = true;
                        break;
                    }
                }

                if (!skip) {
                    bikes.add(bike);
                    bikeIds.add(bike.getId());
                }
            }
        }

        //retrieve parameters
        for (Bike bike : bikes) {
            String drive = getDrive(bike);
            String make = getMake(bike);
            String type = getType(bike);

            BikeShopDto bikeShopDto = new BikeShopDto(bike.getId(), make, bike.getModelName(), type, drive, bike.getPrice(), bike.getBikeIdentificationAvailable().size());
            bikeShopDtoList.add(bikeShopDto);
        }

        return bikeShopDtoList;
    }

    public BikeDetailedInfoDto getDetailedInfoAboutBike(int id) {
        Optional<Bike> bikeOptional = bikeRepository.findById(id);
        if (bikeOptional.isPresent()) {
            Bike bike = bikeOptional.get();
            List<Part> bikeParts = bike.getPart();
            Map<String, String> parts = new HashMap<>();

            //bike attributes
            if (bike.getBikeAttribute() != null) {
                for (BikeAttribute bikeAttribute : bike.getBikeAttribute()) {
                    parts.put(bikeAttribute.getBikeParameterType().getType(), bikeAttribute.getBikeParameterAttribute().getAttribute());
                }
            }

            //part attributes
            if (bikeParts != null) {
                for (Part part : bikeParts) {
                    parts.put(part.getPartParameterAttribute().getPartType().getType(), part.getMake() + " " + part.getModelName() + ", " + part.getPartParameterAttribute().getPartAttribute().toString());
                }
            }

            List<Review> reviews = bike.getReview();
            List<ReviewPrintDto> reviewPrintDtos = new ArrayList<>();
            for (Review review : reviews) {
                ReviewPrintDto reviewPrintDto = new ReviewPrintDto(review.getId(), review.getClient().getUserData().getFirstName(), review.getClient().getUserData().getLastName(), review.getNumberOfStars(), review.getDescription());
                reviewPrintDtos.add(reviewPrintDto);
            }

            //get make
            String make = "";
            for (BikeAttribute bikeAttribute : bike.getBikeAttribute()) {
                if (bikeAttribute.getBikeParameterType().getType().equals("Make")) {
                    make = bikeAttribute.getBikeParameterAttribute().getAttribute();
                }
            }

            BikeDetailedInfoDto bikeDetailedInfoDto = new BikeDetailedInfoDto(bike.getId(), make + " " + bike.getModelName(), bike.getBikeIdentificationAvailable().size(), bike.getPrice(), bike.getDescription(), parts, reviewPrintDtos);
            return bikeDetailedInfoDto;
        }

        return null;
    }

    private ResponseEntity<String> checkAddBikeConstraints(BikeAddDto bikeAddDto){
        if(bikeAddDto.getModelName().equals("")){
            return ResponseEntity.badRequest().body("Error: Model name field is missing");
        }

        if(bikeAddDto.getModelName().length() > 50){
            return ResponseEntity.badRequest().body("Error: Model name can have max. 50 characters");
        }

        if(bikeAddDto.getPrice() == null){
            return ResponseEntity.badRequest().body("Error: Price field is missing");
        }

        if(bikeAddDto.getPrice().scale() > 2) {
            return ResponseEntity.badRequest().body("Error: Price can only have 2 places after comma");
        }

        if(bikeAddDto.getDescription().length() > 500){
            return ResponseEntity.badRequest().body("Error: Description can have max. 500 characters");
        }

        return ResponseEntity.ok().build();
    }

    public ResponseEntity<String> addBike(BikeAddDto bikeAddDto) {
        ResponseEntity<String> response = checkAddBikeConstraints(bikeAddDto);

        if(!response.getStatusCode().equals(HttpStatus.OK)){
            return response;
        }

        Optional<ShopAssistant> shopAssistant = shopAssistantRepository.findById(bikeAddDto.getShopAssistantId());

        if (shopAssistant.isPresent()) {
            Bike bike = new Bike(bikeAddDto.getModelName(), bikeAddDto.getPrice(), bikeAddDto.getDescription(), shopAssistant.get());
            String[] words = bikeAddDto.getBikeIdentificationsAvailable().split(" ");
            List<BikeIdentificationAvailable> bikeIdsAvailable = new ArrayList<>();
            for (String word : words) {
                if(word.length() != 9){
                    return ResponseEntity.badRequest().body("Error: Serial number must consists of exactly 9 digits");
                }
                if(!word.matches("\\d+")){
                    return ResponseEntity.badRequest().body("Error: Serial number must consists of only digits");
                }
                BikeIdentificationReserved bikeIdentificationReserved = bikeIdentificationReservedRepository.findBikeIdentificationReservedBySerialNumber(word);

                if(bikeIdentificationReserved != null){
                    return ResponseEntity.badRequest().body("One of given serial numbers already exists in database");
                }

                BikeIdentificationAvailable bikeIdentificationAvailable = new BikeIdentificationAvailable(word);
                bikeIdentificationAvailableRepository.save(bikeIdentificationAvailable);
                bikeIdentificationAvailable.setBike(bike);
                bikeIdsAvailable.add(bikeIdentificationAvailable);
            }
            bike.setBikeIdentificationAvailable(bikeIdsAvailable);
            List<Part> partsOfBike = new ArrayList<>();

            List<BikeAttribute> bikeAttributes = new ArrayList<>();

            //retrieve make & model name
            for (BikeAddFiltersDto bikeAddFiltersDto : bikeAddDto.getParts()) {

                //if part exist in bike
                if (bikeAddFiltersDto.getAttribute().equals("None")) {

                } else if (bikeAddFiltersDto.getParameter().equals("Type") || bikeAddFiltersDto.getParameter().equals("Frame size") || bikeAddFiltersDto.getParameter().equals("Make")) {
                    BikeAttribute bikeAttribute = bikeAttributeRepository.findBikeAttributeByBikeParameterType_TypeAndBikeParameterAttribute_Attribute(bikeAddFiltersDto.getParameter(), bikeAddFiltersDto.getAttribute());
                    bikeAttributes.add(bikeAttribute);
                } else {
                    int spaceIndex = bikeAddFiltersDto.getAttribute().indexOf(" ");

                    if (spaceIndex != -1) {
                        String firstPart = bikeAddFiltersDto.getAttribute().substring(0, spaceIndex);
                        String secondPart = bikeAddFiltersDto.getAttribute().substring(spaceIndex + 1);

                        String make = firstPart;
                        String modelName = secondPart;

                        Part part = partRepository.findPartByMakeAndModelNameAndPartParameterAttribute_PartType_Type(make, modelName, bikeAddFiltersDto.getParameter());
                        if (part != null) {
                            List<Bike> list = part.getBike();
                            list.add(bike);
                            part.setBike(list);
                            partsOfBike.add(part);
                        }
                    }
                }
            }

            bike.setPart(partsOfBike);
            bike.setBikeAttribute(bikeAttributes);
            bikeRepository.save(bike);
            return ResponseEntity.ok().body("Bike successfully added");
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> deleteBike(Integer id) {
        Optional<Bike> bike = bikeRepository.findById(id);
        if (bike.isPresent()) {
            bikeIdentificationAvailableRepository.deleteAll(bike.get().getBikeIdentificationAvailable());
            bikeRepository.delete(bike.get());
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }
}
