package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.BikeReviewDto;
import com.bikeparadise.bikewebapp.dto.PartReviewDto;
import com.bikeparadise.bikewebapp.model.Bike;
import com.bikeparadise.bikewebapp.model.Client;
import com.bikeparadise.bikewebapp.model.Part;
import com.bikeparadise.bikewebapp.model.Review;
import com.bikeparadise.bikewebapp.repository.BikeRepository;
import com.bikeparadise.bikewebapp.repository.ClientRepository;
import com.bikeparadise.bikewebapp.repository.PartRepository;
import com.bikeparadise.bikewebapp.repository.ReviewRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ReviewService {

    private final ClientRepository clientRepository;
    private final BikeRepository bikeRepository;
    private final ReviewRepository reviewRepository;
    private final PartRepository partRepository;

    public ReviewService(ClientRepository clientRepository, BikeRepository bikeRepository, ReviewRepository reviewRepository, PartRepository partRepository){
        this.clientRepository = clientRepository;
        this.bikeRepository = bikeRepository;
        this.reviewRepository = reviewRepository;
        this.partRepository = partRepository;
    }

    public ResponseEntity<String> postBikeReview(BikeReviewDto bikeReviewDto){
        Optional<Client> clientOptional = clientRepository.findById(bikeReviewDto.getClientId());
        Optional<Bike> bikeOptional = bikeRepository.findById(bikeReviewDto.getBikeId());

        if(clientOptional.isPresent() && bikeOptional.isPresent()){
            Review review = new Review(bikeReviewDto.getNumberOfStars(), bikeReviewDto.getDescription(), clientOptional.get(), bikeOptional.get());
            reviewRepository.save(review);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> postPartReview(PartReviewDto partReviewDto){
        Optional<Client> clientOptional = clientRepository.findById(partReviewDto.getClientId());
        Optional<Part> partOptional = partRepository.findById(partReviewDto.getPartId());

        if(clientOptional.isPresent() && partOptional.isPresent()){
            Review review = new Review(partReviewDto.getNumberOfStars(), partReviewDto.getDescription(), clientOptional.get(), partOptional.get());
            reviewRepository.save(review);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
