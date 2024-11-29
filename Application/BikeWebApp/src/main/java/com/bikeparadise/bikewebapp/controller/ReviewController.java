package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.review.BikeReviewDto;
import com.bikeparadise.bikewebapp.dto.review.PartReviewDto;
import com.bikeparadise.bikewebapp.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
public class ReviewController {

    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService){
        this.reviewService = reviewService;
    }

    @PostMapping("/post-bike-review")
    public ResponseEntity<String> postBikeReview(@RequestBody BikeReviewDto bikeReviewDto){
        return reviewService.postBikeReview(bikeReviewDto);
    }

    @PostMapping("/post-part-review")
    public ResponseEntity<String> postPartReview(@RequestBody PartReviewDto partReviewDto){
        return reviewService.postPartReview(partReviewDto);
    }

    @DeleteMapping("/delete-review")
    public ResponseEntity<String> deleteReview(@RequestParam Integer reviewId){
        return reviewService.deleteReview(reviewId);
    }
}
