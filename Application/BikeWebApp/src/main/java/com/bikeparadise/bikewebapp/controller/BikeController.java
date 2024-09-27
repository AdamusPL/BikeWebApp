package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.model.Bike;
import com.bikeparadise.bikewebapp.service.BikeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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
}
