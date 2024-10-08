package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.PartDto;
import com.bikeparadise.bikewebapp.model.Part;
import com.bikeparadise.bikewebapp.model.PartType;
import com.bikeparadise.bikewebapp.model.ShopAssistant;
import com.bikeparadise.bikewebapp.repository.PartRepository;
import com.bikeparadise.bikewebapp.repository.PartTypeRepository;
import com.bikeparadise.bikewebapp.repository.ShopAssistantRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Part> getParts() {
        return partRepository.findAll();
    }

    public ResponseEntity<String> addPart(PartDto partDto) {
        Optional<PartType> partType = partTypeRepository.findById(partDto.getPartTypeId());
        Optional<ShopAssistant> shopAssistant = shopAssistantRepository.findById(partDto.getShopAssistantId());

        if(partType.isPresent() && shopAssistant.isPresent()){
            Part part = new Part(partDto.getMake(), partDto.getModelName(), partDto.getPrice(), partDto.getQuantityInStock(), partDto.getDescription(), partType.get(), shopAssistant.get());
            partRepository.save(part);
            return ResponseEntity.ok().build();
        }

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