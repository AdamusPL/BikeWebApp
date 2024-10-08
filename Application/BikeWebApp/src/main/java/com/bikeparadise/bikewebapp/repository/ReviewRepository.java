package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
