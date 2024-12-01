package com.bikeparadise.bikewebapp;

import com.bikeparadise.bikewebapp.dto.bike.BikeAddDto;
import com.bikeparadise.bikewebapp.dto.bike.BikeAddFiltersDto;
import com.bikeparadise.bikewebapp.dto.cart.BikeCartDto;
import com.bikeparadise.bikewebapp.dto.cart.CartDto;
import com.bikeparadise.bikewebapp.dto.cart.CartItems;
import com.bikeparadise.bikewebapp.dto.cart.PartCartDto;
import com.bikeparadise.bikewebapp.dto.order.OrderStatusOrderDto;
import com.bikeparadise.bikewebapp.dto.part.PartFiltersDto;
import com.bikeparadise.bikewebapp.dto.part.PartShopDto;
import com.bikeparadise.bikewebapp.dto.part.PartTypeFilterDto;
import com.bikeparadise.bikewebapp.dto.user.UserRegisterDto;
import com.bikeparadise.bikewebapp.model.bike.Bike;
import com.bikeparadise.bikewebapp.model.bike.BikeIdentificationAvailable;
import com.bikeparadise.bikewebapp.model.order.Order;
import com.bikeparadise.bikewebapp.model.order.OrderStatus;
import com.bikeparadise.bikewebapp.model.part.Part;
import com.bikeparadise.bikewebapp.model.part.PartAttribute;
import com.bikeparadise.bikewebapp.model.part.PartParameterAttribute;
import com.bikeparadise.bikewebapp.model.part.PartType;
import com.bikeparadise.bikewebapp.model.review.Review;
import com.bikeparadise.bikewebapp.model.roles.Client;
import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
import com.bikeparadise.bikewebapp.repository.bike.BikeRepository;
import com.bikeparadise.bikewebapp.repository.order.OrderRepository;
import com.bikeparadise.bikewebapp.repository.order.OrderStatusRepository;
import com.bikeparadise.bikewebapp.repository.part.PartAttributeRepository;
import com.bikeparadise.bikewebapp.repository.part.PartParameterAttributeRepository;
import com.bikeparadise.bikewebapp.repository.part.PartRepository;
import com.bikeparadise.bikewebapp.repository.part.PartTypeRepository;
import com.bikeparadise.bikewebapp.repository.review.ReviewRepository;
import com.bikeparadise.bikewebapp.repository.roles.ClientRepository;
import com.bikeparadise.bikewebapp.repository.roles.ShopAssistantRepository;
import com.bikeparadise.bikewebapp.service.*;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UnitTests {

    @Autowired
    BikeService bikeService;
    @Autowired
    BikeRepository bikeRepository;

    @Autowired
    CartService cartService;

    @Autowired
    OrderService orderService;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    OrderStatusRepository orderStatusRepository;

    @Autowired
    PartService partService;
    @Autowired
    PartRepository partRepository;
    @Autowired
    PartTypeRepository partTypeRepository;
    @Autowired
    PartAttributeRepository partAttributeRepository;
    @Autowired
    PartParameterAttributeRepository partParameterAttributeRepository;

    @Autowired
    ReviewService reviewService;
    @Autowired
    ReviewRepository reviewRepository;

    @Autowired
    UserService userService;

    @Autowired
    ShopAssistantRepository shopAssistantRepository;

    @Autowired
    ClientRepository clientRepository;

    @Test
    @Transactional
    public void addBikeTest() {
        //create new shop assistant in DB
        ShopAssistant shopAssistant = new ShopAssistant();
        shopAssistantRepository.save(shopAssistant);

        //chosen parts and parameters for bike
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

        //create new bike
        BikeAddDto bikeAddDto = new BikeAddDto("Swift 4", BigDecimal.valueOf(1999.99),
                "3x7 bike, perfect for starting story with MTB",
                "0123456789, 1234567890, 2345678901", bikeAddFiltersDtoList,
                shopAssistant.getId());

        //tests
        ResponseEntity<String> response = bikeService.addBike(bikeAddDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Transactional
    public void getCartItemsTest() {
        //fill database
        ShopAssistant shopAssistant = new ShopAssistant();
        shopAssistantRepository.save(shopAssistant);
        Bike bike = new Bike("Swift 4", BigDecimal.valueOf(1999.99), "description", shopAssistant);
        List<BikeIdentificationAvailable> bikeIdentificationAvailableList = new ArrayList<>(
                List.of(
                    new BikeIdentificationAvailable("123456789"),
                    new BikeIdentificationAvailable("123456788")
                )
        );
        bike.setBikeIdentificationAvailable(bikeIdentificationAvailableList);
        bikeRepository.save(bike);
        Part part1 = new Part("PFB", "E-500 C", BigDecimal.valueOf(69.99), 20, "description", shopAssistant);
        partRepository.save(part1);
        Part part2 = new Part("PFB", "E-500 RD", BigDecimal.valueOf(49.99), 15, "description", shopAssistant);
        partRepository.save(part2);

        //get items from cart
        List<BikeCartDto> bikeCartDtoList = new ArrayList<>(
                List.of(
                        new BikeCartDto(bike.getId(), 2)
                )
        );

        List<PartCartDto> partCartDtoList = new ArrayList<>(
                List.of(
                        new PartCartDto(part1.getId(), 2),
                        new PartCartDto(part2.getId(), 3)
                )
        );

        CartDto cartDto = new CartDto(bikeCartDtoList, partCartDtoList);
        //get products
        CartItems cartItems = cartService.getCartProducts(cartDto);
        //tests
        assertNotNull(cartItems);
        assertEquals(1, cartItems.getBikes().size());
        assertEquals(2, cartItems.getParts().size());
    }

    @Test
    @Transactional
    public void updateOrderStatusTest() {
        //fill DB with order status
        List<String> statuses = new ArrayList<>(List.of(
                "Ordered", "In-Progress", "Ready to collect", "Completed"
        ));

        for (String status : statuses) {
            OrderStatus orderStatus = new OrderStatus(status);
            orderStatusRepository.save(orderStatus);
        }

        //create new client
        Client client = new Client();
        clientRepository.save(client);

        //create new order
        Date date = new Date();
        Order order = new Order(date, client, orderStatusRepository.findAll().get(0));
        orderRepository.save(order);

        //new order status
        OrderStatusOrderDto orderStatusOrderDto = new OrderStatusOrderDto(orderStatusRepository.findAll().get(3).getId(), order.getId());

        //change order status
        ResponseEntity<String> response = orderService.updateOrderStatus(orderStatusOrderDto);

        //tests
        assertEquals(HttpStatus.OK, response.getStatusCode());
        Optional<Order> orderOptional = orderRepository.findById(orderStatusOrderDto.getOrderId());
        assertTrue(orderOptional.isPresent());
        assertEquals("Completed", orderOptional.get().getOrderStatus().getStatus());
    }

    @Test
    @Transactional
    public void filterPartsByTypeTest() {
        //new shop assistant
        ShopAssistant shopAssistant = new ShopAssistant();
        shopAssistantRepository.save(shopAssistant);

        //new part parameter attributes
        Map<String, List<String>> partParameterAttributes = new HashMap<>() {{
            put("Rear Derailleur", List.of("7 rows", "8 rows"));
            put("Front Derailleur", List.of("7 rows"));
            put("Cassette", List.of("12 rows"));
            put("Chain", List.of("12 rows"));
        }};

        for (Map.Entry<String, List<String>> entry : partParameterAttributes.entrySet()) {
            PartType partType = new PartType(entry.getKey());
            partTypeRepository.save(partType);
            for (String text : entry.getValue()) {
                PartAttribute partAttribute = new PartAttribute(text);
                partAttributeRepository.save(partAttribute);

                PartParameterAttribute partParameterAttribute = new PartParameterAttribute(partType, partAttribute);
                partParameterAttributeRepository.save(partParameterAttribute);
            }
        }

        List<String> modelNames = new ArrayList<>(
                List.of(
                        "E-500 RD", "E-600 RD", "E-500 FD", "E-1200 CS", "E-1200 C"
                )
        );

        List<Integer> quantities = new ArrayList<>(
                List.of(
                        20, 15, 10, 10, 10
                )
        );

        List<BigDecimal> prices = new ArrayList<>(
                List.of(
                        BigDecimal.valueOf(69.99), BigDecimal.valueOf(49.99), BigDecimal.valueOf(39.99), BigDecimal.valueOf(39.99), BigDecimal.valueOf(39.99)
                )
        );

        for (int i = 0; i < 5; i++) {
            //new parts
            Part part = new Part("PFB", modelNames.get(i), prices.get(i), quantities.get(i), "description", partParameterAttributeRepository.findAll().get(i), shopAssistant);
            partRepository.save(part);
        }

        //filters from shop
        List<PartTypeFilterDto> partTypeFilterDtos = new ArrayList<>(
                List.of(
                        new PartTypeFilterDto(1, "Rear Derailleur", true),
                        new PartTypeFilterDto(2, "Front Derailleur", true),
                        new PartTypeFilterDto(3, "Cassette", false),
                        new PartTypeFilterDto(4, "Crankset", false),
                        new PartTypeFilterDto(5, "Front Shifters", false),
                        new PartTypeFilterDto(6, "Rear Shifters", false),
                        new PartTypeFilterDto(7, "Chain", false),
                        new PartTypeFilterDto(8, "Brakes", false),
                        new PartTypeFilterDto(9, "Tyres", false)
                )
        );
        PartFiltersDto partFiltersDto = new PartFiltersDto(partTypeFilterDtos, partRepository.findMinPrice(), partRepository.findMaxPrice());

        List<PartShopDto> partShopDtoList = partService.getFilteredParts(partFiltersDto);
        assertNotNull(partShopDtoList);
        assertEquals(3, partShopDtoList.size());
    }

    @Test
    @Transactional
    public void deleteReviewTest() {
        ShopAssistant shopAssistant = new ShopAssistant();
        shopAssistantRepository.save(shopAssistant);
        Bike bike = new Bike("Swift 4", BigDecimal.valueOf(1999.99), "description", shopAssistant);
        bikeRepository.save(bike);
        Client client = new Client();
        clientRepository.save(client);
        Review review = new Review(5, "Good", client, bike);
        reviewRepository.save(review);

        int reviewsNumber = reviewRepository.findAll().size();
        ResponseEntity<String> response = reviewService.deleteReview(review.getId());

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(reviewsNumber - 1, reviewRepository.findAll().size());
    }

    @Test
    @Transactional
    public void registerShortestDataTest() {
        UserRegisterDto userRegisterDto = new UserRegisterDto(
                "Ab", "Cd", "abcdef", "test1234",
                "a@g", "+48333333333", "test1234", false
        );

        ResponseEntity<String> response = userService.registerUser(userRegisterDto);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    @Transactional
    public void registerNotMatchingPasswordsTest() {
        UserRegisterDto userRegisterDto = new UserRegisterDto(
                "Ab", "Cd", "abcdef", "test1234",
                "a@g", "+48333333333", "test123", false
        );

        ResponseEntity<String> response = userService.registerUser(userRegisterDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Passwords don't match", response.getBody());
    }

    @Test
    @Transactional
    public void registerTooShortFirstName() {
        UserRegisterDto userRegisterDto = new UserRegisterDto(
                "A", "Cd", "abcdef", "test1234",
                "a@g", "+48333333333", "test1234", false
        );

        ResponseEntity<String> response = userService.registerUser(userRegisterDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: First name must have at least 2 characters", response.getBody());
    }

    @Test
    @Transactional
    public void registerTooShortUsername() {
        UserRegisterDto userRegisterDto = new UserRegisterDto(
                "Ab", "Cd", "abcde", "test1234",
                "a@g", "+48333333333", "test1234", false
        );

        ResponseEntity<String> response = userService.registerUser(userRegisterDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Username must have at least 6 characters", response.getBody());
    }

    @Test
    @Transactional
    public void registerWithTooShortPassword() {
        UserRegisterDto userRegisterDto = new UserRegisterDto(
                "Ab", "Cd", "abcdef", "test123",
                "a@g", "+48333333333", "test123", false
        );

        ResponseEntity<String> response = userService.registerUser(userRegisterDto);
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Error: Password must have at least 8 characters", response.getBody());
    }
}
