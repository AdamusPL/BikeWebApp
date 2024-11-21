package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.*;
import com.bikeparadise.bikewebapp.model.*;
import com.bikeparadise.bikewebapp.repository.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class OrderService {

    private final ClientRepository clientRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final PartRepository partRepository;
    private final BikeIdentificationAvailableRepository bikeIdentificationAvailableRepository;
    private final BikeIdentificationReservedRepository bikeIdentificationReservedRepository;
    private final OrderRepository orderRepository;
    private final BikeRepository bikeRepository;
    private final UserRepository userRepository;

    public OrderService(ClientRepository clientRepository, OrderStatusRepository orderStatusRepository,
                        PartRepository partRepository, BikeIdentificationAvailableRepository bikeIdentificationAvailableRepository,
                        OrderRepository orderRepository, BikeRepository bikeRepository,
                        BikeIdentificationReservedRepository bikeIdentificationReservedRepository, UserRepository userRepository) {
        this.clientRepository = clientRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.partRepository = partRepository;
        this.bikeIdentificationAvailableRepository = bikeIdentificationAvailableRepository;
        this.orderRepository = orderRepository;
        this.bikeRepository = bikeRepository;
        this.bikeIdentificationReservedRepository = bikeIdentificationReservedRepository;
        this.userRepository = userRepository;
    }

    public ResponseEntity<String> buy(OrderDto orderDto) {
        //retrieve client id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<User> foundUsers =  userRepository.findUserByUsername(authentication.getName());

        if(foundUsers.size() == 0){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = foundUsers.get(0);

        Optional<Client> client = clientRepository.findById(user.getUserData().getClient().getId());
        //default order status
        List<OrderStatus> orderStatus = orderStatusRepository.findByStatus("Ordered");

        if (client.isPresent() && orderStatus.size() != 0) {
            List<Part> partList = new ArrayList<>();
            if (orderDto.getParts() != null) {
                for (OrderItemDto part : orderDto.getParts()) {
                    Optional<Part> partOptional = partRepository.findById(part.getId());

                    if (partOptional.isPresent()) {
                        partList.add(partOptional.get());

                        //reduce quantityInStock after buying by Client
                        Part get = partOptional.get();
                        get.setQuantityInStock(get.getQuantityInStock() - part.getQuantity());
                        partRepository.save(get);
                    }
                }
            }

            List<BikeIdentificationReserved> bikeIdentificationReservedList = new ArrayList<>();
            if (orderDto.getBikes() != null) {
                for (OrderItemDto bike : orderDto.getBikes()) {
                    Optional<Bike> bikeOptional = bikeRepository.findById(bike.getId());

                    if (bikeOptional.isPresent()) {
                        //reduce quantityInStock after buying by Client
                        Bike actualBike = bikeOptional.get();
                        bikeRepository.save(actualBike);

                        //assign bike with identification to Client, first from up which is available
                        BikeIdentificationAvailable bikeIdentificationAvailable = actualBike.getBikeIdentificationAvailable().get(0);
                        BikeIdentificationReserved bikeIdentificationReserved = new BikeIdentificationReserved(bikeIdentificationAvailable.getSerialNumber(), bikeIdentificationAvailable.getBike());

                        //move available bike identification to entity with reserved identifications
                        bikeIdentificationReservedRepository.save(bikeIdentificationReserved);
                        bikeIdentificationReservedList.add(bikeIdentificationReserved);
                        bikeIdentificationAvailableRepository.delete(bikeIdentificationAvailable);
                    }
                }
            }

            Date date = new Date();
            Order order = new Order(date, client.get(), orderStatus.get(0), partList, bikeIdentificationReservedList);
            orderRepository.save(order);

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public ResponseEntity<List<OrderListDto>> getOrderList(){
        //retrieve client id
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        List<User> foundUsers =  userRepository.findUserByUsername(authentication.getName());

        if(foundUsers.size() == 0){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        User user = foundUsers.get(0);

        List<OrderListDto> score = new ArrayList<>();
        List<Order> orderList = orderRepository.findByClientId(user.getUserData().getClient().getId());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for(Order order : orderList){
            List<OrderListBikeDto> orderedBikes = new ArrayList<>();
            List<OrderListPartDto> orderedParts = new ArrayList<>();
            BigDecimal finalPrice = new BigDecimal(0);

            List<Integer> bikesSearched = new ArrayList<>();
            for(BikeIdentificationReserved bikeIdentificationReserved : order.getBikeIdentificationReserved()){
                boolean stop = false;
                for(Integer bikeId : bikesSearched){
                    if(Objects.equals(bikeId, bikeIdentificationReserved.getBike().getId())){
                        for(OrderListBikeDto orderListBikeDto : orderedBikes){
                            if(orderListBikeDto.getId().equals(bikeId)){
                                orderListBikeDto.setQuantity(orderListBikeDto.getQuantity() + 1);
                                stop = true;
                            }
                            if(stop){
                                break;
                            }
                        }
                    }
                    if(stop){
                        break;
                    }
                }
                if(stop){
                    continue;
                }
                orderedBikes.add(new OrderListBikeDto(bikeIdentificationReserved.getBike().getId(), bikeIdentificationReserved.getBike().getModelName() + " " + bikeIdentificationReserved.getBike().getModelName(), bikeIdentificationReserved.getBike().getPrice(), 1));
                finalPrice = finalPrice.add(bikeIdentificationReserved.getBike().getPrice());
                bikesSearched.add(bikeIdentificationReserved.getBike().getId());
            }
            for(Part part : order.getPart()){
                orderedParts.add(new OrderListPartDto(part.getId(), part.getMake() + " " + part.getModelName(), part.getPrice(), 1));
                finalPrice = finalPrice.add(part.getPrice());
            }
            OrderListDto orderListDto = new OrderListDto(order.getId(), formatter.format(order.getOrderDate()), order.getOrderStatus().getStatus(), orderedBikes, orderedParts, finalPrice);
            score.add(orderListDto);
        }

        return ResponseEntity.ok(score);
    }

    public List<OrderListDto> getAllOrdersList(){
        List<OrderListDto> score = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for(Order order : orderList){
            List<OrderListBikeDto> orderedBikes = new ArrayList<>();
            List<OrderListPartDto> orderedParts = new ArrayList<>();
            BigDecimal finalPrice = new BigDecimal(0);

            List<Integer> bikesSearched = new ArrayList<>();
            for(BikeIdentificationReserved bikeIdentificationReserved : order.getBikeIdentificationReserved()){
                boolean stop = false;
                for(Integer bikeId : bikesSearched){
                    if(Objects.equals(bikeId, bikeIdentificationReserved.getBike().getId())){
                        for(OrderListBikeDto orderListBikeDto : orderedBikes){
                            if(orderListBikeDto.getId().equals(bikeId)){
                                orderListBikeDto.setQuantity(orderListBikeDto.getQuantity() + 1);
                                stop = true;
                            }
                            if(stop){
                                break;
                            }
                        }
                    }
                    if(stop){
                        break;
                    }
                }
                if(stop){
                    continue;
                }
                orderedBikes.add(new OrderListBikeDto(bikeIdentificationReserved.getBike().getId(), bikeIdentificationReserved.getBike().getModelName() + " " + bikeIdentificationReserved.getBike().getModelName(), bikeIdentificationReserved.getBike().getPrice(), 1));
                finalPrice = finalPrice.add(bikeIdentificationReserved.getBike().getPrice());
                bikesSearched.add(bikeIdentificationReserved.getBike().getId());
            }
            for(Part part : order.getPart()){
                orderedParts.add(new OrderListPartDto(part.getId(), part.getMake() + " " + part.getModelName(), part.getPrice(), 1));
                finalPrice = finalPrice.add(part.getPrice());
            }
            OrderListDto orderListDto = new OrderListDto(order.getId(), formatter.format(order.getOrderDate()), order.getOrderStatus().getStatus(), orderedBikes, orderedParts, finalPrice);
            score.add(orderListDto);
        }

        return score;
    }

    public ResponseEntity<String> updateOrderStatus(OrderStatusOrderDto orderStatusOrderDto){
        Optional<Order> orderOptional = orderRepository.findById(orderStatusOrderDto.getOrderId());

        if(orderOptional.isPresent()){
            Optional<OrderStatus> orderStatusOptional = orderStatusRepository.findById(orderStatusOrderDto.getId());
            if(orderStatusOptional.isPresent()){
                Order order = orderOptional.get();
                order.setOrderStatus(orderStatusOptional.get());
                orderRepository.save(order);
                return ResponseEntity.ok().build();
            }
        }

        return ResponseEntity.notFound().build();

    }

    public List<OrderStatusDto> getOrderStatuses(){
        List<OrderStatus> orderStatuses = orderStatusRepository.findAll();
        List<OrderStatusDto> orderStatusDtos = new ArrayList<>();

        for(OrderStatus orderStatus : orderStatuses){
            OrderStatusDto orderStatusDto = new OrderStatusDto(orderStatus.getId(), orderStatus.getStatus());
            orderStatusDtos.add(orderStatusDto);
        }

        return orderStatusDtos;

    }
}
