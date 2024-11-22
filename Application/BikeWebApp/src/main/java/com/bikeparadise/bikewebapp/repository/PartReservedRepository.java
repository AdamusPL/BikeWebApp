package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.PartReserved;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartReservedRepository extends JpaRepository<PartReserved, Integer> {
}
