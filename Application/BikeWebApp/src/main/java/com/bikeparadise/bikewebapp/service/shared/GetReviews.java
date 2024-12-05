package com.bikeparadise.bikewebapp.service.shared;

import com.bikeparadise.bikewebapp.dto.review.ReviewPrintDto;
import com.bikeparadise.bikewebapp.model.review.Review;

import java.util.ArrayList;
import java.util.List;

public class GetReviews {
    public static List<ReviewPrintDto> getReviews(List<Review> reviews) {
        List<ReviewPrintDto> reviewPrintDtos = new ArrayList<>();
        for (Review review : reviews) {
            ReviewPrintDto reviewPrintDto = new ReviewPrintDto(review.getId(), review.getClient().getUserData().getFirstName(), review.getClient().getUserData().getLastName(), review.getNumberOfStars(), review.getDescription());
            reviewPrintDtos.add(reviewPrintDto);
        }
        return reviewPrintDtos;
    }
}
