package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.OrderDto;
import com.bikeparadise.bikewebapp.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService){
        this.orderService = orderService;
    }

    @PostMapping("/buy")
    public ResponseEntity<String> buy(@RequestBody OrderDto orderDto){
        return orderService.buy(orderDto);
    }

}
