package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.review.BikeReviewDto;
import com.bikeparadise.bikewebapp.dto.review.PartReviewDto;
import com.bikeparadise.bikewebapp.model.bike.Bike;
import com.bikeparadise.bikewebapp.model.part.Part;
import com.bikeparadise.bikewebapp.model.review.Review;
import com.bikeparadise.bikewebapp.model.roles.Client;
import com.bikeparadise.bikewebapp.model.user.User;
import com.bikeparadise.bikewebapp.repository.bike.BikeRepository;
import com.bikeparadise.bikewebapp.repository.part.PartRepository;
import com.bikeparadise.bikewebapp.repository.review.ReviewRepository;
import com.bikeparadise.bikewebapp.repository.roles.ClientRepository;
import com.bikeparadise.bikewebapp.repository.user.UserRepository;
import com.bikeparadise.bikewebapp.service.shared.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    private final ClientRepository clientRepository;
    private final BikeRepository bikeRepository;
    private final ReviewRepository reviewRepository;
    private final PartRepository partRepository;
    private final UserRepository userRepository;

    public ReviewService(ClientRepository clientRepository, BikeRepository bikeRepository, ReviewRepository reviewRepository, PartRepository partRepository, UserRepository userRepository){
        this.clientRepository = clientRepository;
        this.bikeRepository = bikeRepository;
        this.reviewRepository = reviewRepository;
        this.partRepository = partRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> postBikeReview(BikeReviewDto bikeReviewDto){
        if(bikeReviewDto.getNumberOfStars() < 1 || bikeReviewDto.getNumberOfStars() > 5){
            return ResponseEntity.badRequest().body("Error: You have to review in scale 1 to 5");
        }
        if(bikeReviewDto.getDescription().length() > 500){
            return ResponseEntity.badRequest().body("Error: Review text can't be longer than 500 characters");
        }

        User user = AuthService.checkAuth(userRepository);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Client> clientOptional = clientRepository.findById(user.getUserData().getClient().getId());
        Optional<Bike> bikeOptional = bikeRepository.findById(bikeReviewDto.getBikeId());

        if(clientOptional.isPresent() && bikeOptional.isPresent()){
            Review review = new Review(bikeReviewDto.getNumberOfStars(), bikeReviewDto.getDescription(), clientOptional.get(), bikeOptional.get());
            reviewRepository.save(review);
            return ResponseEntity.ok().body("Review posted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> postPartReview(PartReviewDto partReviewDto){
        if(partReviewDto.getNumberOfStars() < 1 || partReviewDto.getNumberOfStars() > 5){
            return ResponseEntity.badRequest().body("Error: You have to review in scale 1 to 5");
        }
        if(partReviewDto.getDescription().length() > 500){
            return ResponseEntity.badRequest().body("Error: Review text can't be longer than 500 characters");
        }

        User user = AuthService.checkAuth(userRepository);
        if(user == null){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        Optional<Client> clientOptional = clientRepository.findById(user.getUserData().getClient().getId());
        Optional<Part> partOptional = partRepository.findById(partReviewDto.getPartId());

        if(clientOptional.isPresent() && partOptional.isPresent()){
            Review review = new Review(partReviewDto.getNumberOfStars(), partReviewDto.getDescription(), clientOptional.get(), partOptional.get());
            reviewRepository.save(review);
            return ResponseEntity.ok().body("Review posted successfully");
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> deleteReview(int reviewId){
        Optional<Review> reviewOptional = reviewRepository.findById(reviewId);

        if(reviewOptional.isPresent()){
            Review review = reviewOptional.get();
            reviewRepository.delete(review);
            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();

    }
}
