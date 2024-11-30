package com.bikeparadise.bikewebapp.repository.part;

import com.bikeparadise.bikewebapp.model.part.PartParameterAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartParameterAttributeRepository extends JpaRepository<PartParameterAttribute, Integer> {
}
