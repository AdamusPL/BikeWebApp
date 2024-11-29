package com.bikeparadise.bikewebapp.repository.part;

import com.bikeparadise.bikewebapp.model.part.PartAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartAttributeRepository extends JpaRepository<PartAttribute, Integer> {
    PartAttribute findFirstByAttribute(String attribute);
}
