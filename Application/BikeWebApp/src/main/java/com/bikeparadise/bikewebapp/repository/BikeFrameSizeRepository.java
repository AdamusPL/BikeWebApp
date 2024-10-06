package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.BikeFrameSize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BikeFrameSizeRepository extends JpaRepository<BikeFrameSize, Integer> {
}
