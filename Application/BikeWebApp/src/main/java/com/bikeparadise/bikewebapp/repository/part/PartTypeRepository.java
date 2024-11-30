package com.bikeparadise.bikewebapp.repository.part;

import com.bikeparadise.bikewebapp.model.part.PartType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartTypeRepository extends JpaRepository<PartType, Integer> {
    PartType findFirstByType(String type);
}
