package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.OrderDto;
import com.bikeparadise.bikewebapp.dto.OrderListDto;
import com.bikeparadise.bikewebapp.model.Order;
import com.bikeparadise.bikewebapp.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get-order-list")
    @ResponseBody
    public List<OrderListDto> getOrderList(@RequestParam int clientId){
        return orderService.getOrderList(clientId);
    }

}
