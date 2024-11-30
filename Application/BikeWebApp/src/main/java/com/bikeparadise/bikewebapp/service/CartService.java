package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.cart.BikeCartDto;
import com.bikeparadise.bikewebapp.dto.cart.BikeCartInfo;
import com.bikeparadise.bikewebapp.dto.cart.CartDto;
import com.bikeparadise.bikewebapp.dto.cart.CartItems;
import com.bikeparadise.bikewebapp.dto.cart.PartCartDto;
import com.bikeparadise.bikewebapp.dto.cart.PartCartInfo;
import com.bikeparadise.bikewebapp.model.bike.Bike;
import com.bikeparadise.bikewebapp.model.bike.BikeAttribute;
import com.bikeparadise.bikewebapp.model.part.Part;
import com.bikeparadise.bikewebapp.repository.bike.BikeRepository;
import com.bikeparadise.bikewebapp.repository.part.PartRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CartService {

    private final PartRepository partRepository;
    private final BikeRepository bikeRepository;

    public CartService(PartRepository partRepository, BikeRepository bikeRepository) {
        this.partRepository = partRepository;
        this.bikeRepository = bikeRepository;
    }

    public CartItems getCartProducts(CartDto cartDto) {
        List<PartCartInfo> cartParts = new ArrayList<>();

        for (PartCartDto partCartDto : cartDto.getParts()) {
            Optional<Part> partOptional = partRepository.findById(partCartDto.getId());
            if (partOptional.isPresent()) {
                Part part = partOptional.get();
                PartCartInfo productDto = new PartCartInfo(part.getId(), part.getMake() + " " + part.getModelName(), part.getPrice(), partCartDto.getQuantity(), part.getQuantityInStock());
                cartParts.add(productDto);
            }
        }

        List<BikeCartInfo> cartBikes = new ArrayList<>();

        for (BikeCartDto bikeCartDto : cartDto.getBikes()) {
            Optional<Bike> bikeOptional = bikeRepository.findById(bikeCartDto.getId());
            if (bikeOptional.isPresent()) {
                Bike bike = bikeOptional.get();
                String make = "";
                for (BikeAttribute bikeAttribute : bike.getBikeAttribute()) {
                    if (bikeAttribute.getBikeParameterType().getType().equals("Make")) {
                        make = bikeAttribute.getBikeParameterAttribute().getAttribute();
                    }
                }
                BikeCartInfo productDto = new BikeCartInfo(bike.getId(), make + " " + bike.getModelName(), bike.getPrice(), bikeCartDto.getQuantity(), bike.getBikeIdentificationAvailable().size());
                cartBikes.add(productDto);
            }
        }

        CartItems cartItems = new CartItems(cartBikes, cartParts);

        return cartItems;
    }
}
