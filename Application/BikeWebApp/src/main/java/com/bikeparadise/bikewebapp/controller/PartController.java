package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.part.*;
import com.bikeparadise.bikewebapp.service.PartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class PartController {

    private final PartService partService;

    public PartController(PartService partService){
        this.partService = partService;
    }

    @GetMapping("/get-detailed-info-about-part")
    @ResponseBody
    public PartDetailedInfoDto getPartDetailedInfo(@RequestParam int partId){
        return partService.getDetailedInfoAboutPart(partId);
    }

    @GetMapping("/get-part-filters")
    @ResponseBody
    public PartFiltersDto getFilters(){
        return partService.getShopFilters();
    }

    @PostMapping("/filter-parts-by-type")
    @ResponseBody
    public List<PartShopDto> getPartsByType(@RequestBody PartFiltersDto partTypeFilterDtos){
        return partService.getFilteredParts(partTypeFilterDtos);
    }

    @GetMapping("/get-add-part-filters")
    @ResponseBody
    public Map<String, List<String>> getAddPartFilters(){
        return partService.getAddPartFilters();
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
