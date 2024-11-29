package com.bikeparadise.bikewebapp.repository.review;

import com.bikeparadise.bikewebapp.model.review.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
}
