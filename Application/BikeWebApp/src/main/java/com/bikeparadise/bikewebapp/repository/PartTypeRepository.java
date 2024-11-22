package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.PartType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartTypeRepository extends JpaRepository<PartType, Integer> {
    PartType findFirstByType(String type);
}
