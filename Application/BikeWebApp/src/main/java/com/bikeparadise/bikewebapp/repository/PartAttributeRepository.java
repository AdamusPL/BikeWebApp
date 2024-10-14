package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.PartAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PartAttributeRepository extends JpaRepository<PartAttribute, Integer> {
    List<PartAttribute> findByAttribute(String attribute);
}
