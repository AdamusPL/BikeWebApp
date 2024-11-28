package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.BikeDetailedInfoDto;
import com.bikeparadise.bikewebapp.dto.BikeAddDto;
import com.bikeparadise.bikewebapp.dto.BikeShopDto;
import com.bikeparadise.bikewebapp.dto.FilterDto;
import com.bikeparadise.bikewebapp.model.Bike;
import com.bikeparadise.bikewebapp.service.BikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class BikeController {
    private final BikeService bikeService;

    public BikeController(BikeService bikeService){
        this.bikeService = bikeService;
    }

    @GetMapping("/bike-shop")
    @ResponseBody
    public List<BikeShopDto> bikeShop(){
        return bikeService.getBikes();
    }

    @GetMapping("/get-detailed-info-about-bike")
    @ResponseBody
    public BikeDetailedInfoDto getDetailedInfoBike(@RequestParam int bikeId){
        return bikeService.getDetailedInfoAboutBike(bikeId);
    }

    @GetMapping("/get-bike-shop-filters")
    @ResponseBody
    public List<FilterDto> getShopFilters(){
        return bikeService.getShopFilters();
    }

    @GetMapping("/get-add-bike-filters")
    @ResponseBody
    public Map<String, List<String>> getAddBikeFilters(){
        return bikeService.getAddBikeFilters();
    }

    @PostMapping("/filter-bikes")
    @ResponseBody
    public List<BikeShopDto> getFilteredBikes(@RequestBody List<FilterDto> filters){
        return bikeService.getFilteredBikes(filters);
    }

    @GetMapping("/get-bike-by-price-range")
    public List<Bike> getBikeByPriceRange(@RequestParam Double lowerRange, @RequestParam Double upperRange){
        return bikeService.getBikeByPrice(lowerRange, upperRange);
    }

    @PostMapping("/add-bike")
    public ResponseEntity<String> addBike(@RequestBody BikeAddDto bikeAddDto){
        return bikeService.addBike(bikeAddDto);
    }

    @DeleteMapping("/delete-bike")
    public ResponseEntity<String> deleteBike(@RequestParam Integer bikeId){
        return bikeService.deleteBike(bikeId);
    }
}
