package com.bikeparadise.bikewebapp.service;

import com.bikeparadise.bikewebapp.dto.*;
import com.bikeparadise.bikewebapp.model.*;
import com.bikeparadise.bikewebapp.repository.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final ClientRepository clientRepository;
    private final OrderStatusRepository orderStatusRepository;
    private final PartRepository partRepository;
    private final BikeIdentificationAvailableRepository bikeIdentificationAvailableRepository;
    private final BikeIdentificationReservedRepository bikeIdentificationReservedRepository;
    private final OrderRepository orderRepository;
    private final BikeRepository bikeRepository;

    public OrderService(ClientRepository clientRepository, OrderStatusRepository orderStatusRepository,
                        PartRepository partRepository, BikeIdentificationAvailableRepository bikeIdentificationAvailableRepository,
                        OrderRepository orderRepository, BikeRepository bikeRepository, BikeIdentificationReservedRepository bikeIdentificationReservedRepository) {
        this.clientRepository = clientRepository;
        this.orderStatusRepository = orderStatusRepository;
        this.partRepository = partRepository;
        this.bikeIdentificationAvailableRepository = bikeIdentificationAvailableRepository;
        this.orderRepository = orderRepository;
        this.bikeRepository = bikeRepository;
        this.bikeIdentificationReservedRepository = bikeIdentificationReservedRepository;
    }

    public ResponseEntity<String> buy(OrderDto orderDto) {
        Optional<Client> client = clientRepository.findById(orderDto.getClientId());
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

                        Date date = new Date();
                        Order order = new Order(date, client.get(), orderStatus.get(0), partList, bikeIdentificationReservedList);
                        orderRepository.save(order);
                    }
                }
            }

            return ResponseEntity.ok().build();
        }

        return ResponseEntity.notFound().build();
    }

    public List<OrderListDto> getOrderList(int clientId){
        List<OrderListDto> score = new ArrayList<>();
        List<Order> orderList = orderRepository.findByClientId(clientId);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for(Order order : orderList){
            List<OrderListBikeDto> orderedBikes = new ArrayList<>();
            List<OrderListPartDto> orderedParts = new ArrayList<>();
            BigDecimal finalPrice = new BigDecimal(0);
            for(BikeIdentificationReserved bikeIdentificationReserved : order.getBikeIdentificationReserved()){
                orderedBikes.add(new OrderListBikeDto(bikeIdentificationReserved.getBike().getId(), bikeIdentificationReserved.getBike().getModelName() + " " + bikeIdentificationReserved.getBike().getModelName()));
                finalPrice = finalPrice.add(bikeIdentificationReserved.getBike().getPrice());
            }
            for(Part part : order.getPart()){
                orderedParts.add(new OrderListPartDto(part.getId(), part.getMake() + " " + part.getModelName()));
                finalPrice = finalPrice.add(part.getPrice());
            }
            OrderListDto orderListDto = new OrderListDto(order.getId(), formatter.format(order.getOrderDate()), order.getOrderStatus().getStatus(), orderedBikes, orderedParts, finalPrice);
            score.add(orderListDto);
        }

        return score;
    }

    public List<OrderListDto> getAllOrdersList(){
        List<OrderListDto> score = new ArrayList<>();
        List<Order> orderList = orderRepository.findAll();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for(Order order : orderList){
            List<OrderListBikeDto> orderedBikes = new ArrayList<>();
            List<OrderListPartDto> orderedParts = new ArrayList<>();
            BigDecimal finalPrice = new BigDecimal(0);
            for(BikeIdentificationReserved bikeIdentificationReserved : order.getBikeIdentificationReserved()){
                orderedBikes.add(new OrderListBikeDto(bikeIdentificationReserved.getBike().getId(), bikeIdentificationReserved.getBike().getModelName() + " " + bikeIdentificationReserved.getBike().getModelName()));
                finalPrice = finalPrice.add(bikeIdentificationReserved.getBike().getPrice());
            }
            for(Part part : order.getPart()){
                orderedParts.add(new OrderListPartDto(part.getId(), part.getMake() + " " + part.getModelName()));
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
