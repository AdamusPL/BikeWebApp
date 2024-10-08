package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.BikeDetailedInfoDto;
import com.bikeparadise.bikewebapp.dto.BikeDto;
import com.bikeparadise.bikewebapp.model.Bike;
import com.bikeparadise.bikewebapp.service.BikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class BikeController {
    private final BikeService bikeService;

    public BikeController(BikeService bikeService){
        this.bikeService = bikeService;
    }

    @GetMapping("/bike-shop")
    @ResponseBody
    public List<Bike> bikeShop(){
        return bikeService.getBikes();
    }

    @GetMapping("/get-detailed-info-about-bike")
    @ResponseBody
    public BikeDetailedInfoDto getDetailedInfoBike(@RequestParam int bikeId){
        return bikeService.getDetailedInfoAboutBike(bikeId);
    }

    @GetMapping("/get-bike-by-frame-size")
    public List<Bike> getBikeByFrameSize(@RequestParam String frameSize){
        return bikeService.getBikeByFrameSize(frameSize);
    }

    @GetMapping("/get-bike-by-type")
    public List<Bike> getBikeByType(@RequestParam String type){
        return bikeService.getBikeByType(type);
    }

    @GetMapping("/get-bike-by-price-range")
    public List<Bike> getBikeByPriceRange(@RequestParam Double lowerRange, @RequestParam Double upperRange){
        return bikeService.getBikeByPrice(lowerRange, upperRange);
    }

    @GetMapping("/get-bike-by-make")
    public List<Bike> getBikeByMake(@RequestParam String make){
        return bikeService.getBikeByMake(make);
    }

    @PostMapping("/add-bike")
    public ResponseEntity<String> addBike(@RequestBody BikeDto bikeDto){
        return bikeService.addBike(bikeDto);
    }

    @DeleteMapping("/delete-bike")
    public ResponseEntity<String> deleteBike(@RequestParam Integer bikeId){
        return bikeService.deleteBike(bikeId);
    }
}
