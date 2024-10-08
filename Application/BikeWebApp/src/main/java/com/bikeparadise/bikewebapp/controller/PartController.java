package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.PartDto;
import com.bikeparadise.bikewebapp.model.Part;
import com.bikeparadise.bikewebapp.service.PartService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class PartController {

    private final PartService partService;

    public PartController(PartService partService){
        this.partService = partService;
    }

    @GetMapping("/part-shop")
    @ResponseBody
    public List<Part> getParts(){
        return partService.getParts();
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