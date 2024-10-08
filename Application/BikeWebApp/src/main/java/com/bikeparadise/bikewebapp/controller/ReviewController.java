package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.BikeReviewDto;
import com.bikeparadise.bikewebapp.dto.PartReviewDto;
import com.bikeparadise.bikewebapp.service.ReviewService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
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
}
