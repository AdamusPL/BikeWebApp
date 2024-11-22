package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.BikeReviewDto;
import com.bikeparadise.bikewebapp.dto.PartReviewDto;
import com.bikeparadise.bikewebapp.model.*;
import com.bikeparadise.bikewebapp.repository.*;
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
        //retrieve client id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<User> foundUsers = userRepository.findUserByUsername(authentication.getName());

        if (foundUsers.size() == 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = foundUsers.get(0);

        Optional<Client> clientOptional = clientRepository.findById(user.getUserData().getClient().getId());
        Optional<Bike> bikeOptional = bikeRepository.findById(bikeReviewDto.getBikeId());

        if(clientOptional.isPresent() && bikeOptional.isPresent()){
            Review review = new Review(bikeReviewDto.getNumberOfStars(), bikeReviewDto.getDescription(), clientOptional.get(), bikeOptional.get());
            reviewRepository.save(review);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<String> postPartReview(PartReviewDto partReviewDto){
        //retrieve client id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<User> foundUsers = userRepository.findUserByUsername(authentication.getName());

        if (foundUsers.size() == 0) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = foundUsers.get(0);

        Optional<Client> clientOptional = clientRepository.findById(user.getUserData().getClient().getId());
        Optional<Part> partOptional = partRepository.findById(partReviewDto.getPartId());

        if(clientOptional.isPresent() && partOptional.isPresent()){
            Review review = new Review(partReviewDto.getNumberOfStars(), partReviewDto.getDescription(), clientOptional.get(), partOptional.get());
            reviewRepository.save(review);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}
