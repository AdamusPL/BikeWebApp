package com.bikeparadise.bikewebapp;

import com.bikeparadise.bikewebapp.dto.bike.BikeAddDto;
import com.bikeparadise.bikewebapp.dto.bike.BikeAddFiltersDto;
import com.bikeparadise.bikewebapp.dto.cart.BikeCartDto;
import com.bikeparadise.bikewebapp.dto.cart.CartDto;
import com.bikeparadise.bikewebapp.dto.cart.CartItems;
import com.bikeparadise.bikewebapp.dto.cart.PartCartDto;
import com.bikeparadise.bikewebapp.dto.order.OrderStatusOrderDto;
import com.bikeparadise.bikewebapp.model.order.Order;
import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
import com.bikeparadise.bikewebapp.repository.order.OrderRepository;
import com.bikeparadise.bikewebapp.repository.roles.ShopAssistantRepository;
import com.bikeparadise.bikewebapp.service.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UnitTests {

    @Autowired
    BikeService bikeService;

    @Autowired
    ShopAssistantRepository shopAssistantRepository;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PartService partService;

    @Autowired
    ReviewService reviewService;

    @Autowired
    UserService userService;

    @Test
    @Transactional
    public void addBikeTest(){
        ShopAssistant shopAssistant = new ShopAssistant();
        shopAssistantRepository.save(shopAssistant);

        List<BikeAddFiltersDto> bikeAddFiltersDtoList = new ArrayList<>(List.of(
                new BikeAddFiltersDto("Make", "AeroBike"),
                new BikeAddFiltersDto("Frame size", "M"),
                new BikeAddFiltersDto("Type", "MTB"),
                new BikeAddFiltersDto("Rear Derailleur", "PFB E-500 RD"),
                new BikeAddFiltersDto("Chain", "ClunkyJ a8"),
                new BikeAddFiltersDto("Cassette", "PFB E-500 C"),
                new BikeAddFiltersDto("Rear Shifters", "PFB E-500 RS"),
                new BikeAddFiltersDto("Front Shifters", "PFB E-500 FS"),
                new BikeAddFiltersDto("Tyres", "SwiftZPart Bumpy"),
                new BikeAddFiltersDto("Front Derailleur", "PFB E-500 FD")
                )
        );

        BikeAddDto bikeAddDto = new BikeAddDto("Swift 4", BigDecimal.valueOf(1999.99),
                "3x7 bike, perfect for starting story with MTB",
                "0123456789, 1234567890, 2345678901", bikeAddFiltersDtoList,
                shopAssistant.getId());

        ResponseEntity<String> response = bikeService.addBike(bikeAddDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void getCartItemsTest(){
        List<BikeCartDto> bikeCartDtoList = new ArrayList<>(
                List.of(
                        new BikeCartDto(1, 3)
                )
        );

        List<PartCartDto> partCartDtoList = new ArrayList<>(
                List.of(
                        new PartCartDto(1, 2),
                        new PartCartDto(2, 3)
                )
        );

        CartDto cartDto = new CartDto(bikeCartDtoList, partCartDtoList);
        CartItems cartItems = cartService.getCartProducts(cartDto);
        assertNotNull(cartItems);
        assertEquals(cartItems.getBikes().size(), 1);
        assertEquals(cartItems.getParts().size(), 2);
    }

    @Test
    @Transactional
    public void updateOrderStatusTest(){
        OrderStatusOrderDto orderStatusOrderDto = new OrderStatusOrderDto(4, 1002);

        ResponseEntity<String> response = orderService.updateOrderStatus(orderStatusOrderDto);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<Order> order = orderRepository.findById(orderStatusOrderDto.getOrderId());
        assertTrue(order.isPresent());
        assertEquals(order.get().getOrderStatus().getStatus(), "Completed");
    }

    @Test
    public void filterPartsByTypeTest(){
        
    }

    @Test
    @Transactional
    public void deleteReviewTest(){

    }

}
