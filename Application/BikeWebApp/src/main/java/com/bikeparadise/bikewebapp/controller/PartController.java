package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.part.PartDetailedInfoDto;
import com.bikeparadise.bikewebapp.dto.part.PartDto;
import com.bikeparadise.bikewebapp.dto.part.PartShopDto;
import com.bikeparadise.bikewebapp.dto.part.PartTypeFilterDto;
import com.bikeparadise.bikewebapp.service.PartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService){
        this.partService = partService;
    }

    @GetMapping("/part-shop")
    @ResponseBody
    public List<PartShopDto> getParts(){
        return partService.getParts();
    }

    @GetMapping("/get-detailed-info-about-part")
    @ResponseBody
    public PartDetailedInfoDto getPartDetailedInfo(@RequestParam int partId){
        return partService.getDetailedInfoAboutPart(partId);
    }

    @GetMapping("/get-part-filters")
    @ResponseBody
    public List<PartTypeFilterDto> getFilters(){
        return partService.getShopFilters();
    }

    @PostMapping("/filter-parts-by-type")
    @ResponseBody
    public List<PartShopDto> getPartsByType(@RequestBody List<PartTypeFilterDto> partTypeFilterDtos){
        return partService.getFilteredParts(partTypeFilterDtos);
    }

    @GetMapping("/get-add-part-filters")
    @ResponseBody
    public List<PartTypeFilterDto> getAddPartFilters(){
        return partService.getShopFilters();
    }

    @PostMapping("/add-part")
    public ResponseEntity<String> addPart(@RequestBody PartDto partDto){
        return partService.addPart(partDto);
    }

    @DeleteMapping("/delete-part")
    public ResponseEntity<String> deletePart(@RequestParam Integer partId){
        return partService.deletePart(partId);
    }
}
