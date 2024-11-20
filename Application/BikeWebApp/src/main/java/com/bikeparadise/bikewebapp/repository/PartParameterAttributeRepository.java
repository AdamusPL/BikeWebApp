package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.PartParameterAttribute;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PartParameterAttributeRepository extends JpaRepository<PartParameterAttribute, Integer> {
    PartParameterAttribute findByPartType_TypeAndPartAttribute_Attribute(String partType, String partAttribute);
}
