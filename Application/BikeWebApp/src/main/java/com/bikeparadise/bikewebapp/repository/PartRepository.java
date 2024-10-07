package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartRepository extends JpaRepository<Part, Integer> {
}
