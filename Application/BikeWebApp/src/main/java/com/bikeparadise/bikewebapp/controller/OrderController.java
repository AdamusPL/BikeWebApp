package com.bikeparadise.bikewebapp.controller;

import com.bikeparadise.bikewebapp.dto.order.OrderDto;
import com.bikeparadise.bikewebapp.dto.order.OrderListDto;
import com.bikeparadise.bikewebapp.dto.order.OrderStatusDto;
import com.bikeparadise.bikewebapp.dto.order.OrderStatusOrderDto;
import com.bikeparadise.bikewebapp.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@CrossOrigin(origins = "http://localhost:3000")
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
    public ResponseEntity<List<OrderListDto>> getOrderList(){
        return orderService.getOrderList();
    }

    @GetMapping("/get-all-orders-list")
    @ResponseBody
    public List<OrderListDto> getAllOrdersList(){
        return orderService.getAllOrdersList();
    }

    @PutMapping("/update-order-status")
    public ResponseEntity<String> updateOrderStatus(@RequestBody OrderStatusOrderDto orderStatusOrderDto){
        return orderService.updateOrderStatus(orderStatusOrderDto);
    }

    @GetMapping("/get-order-statuses")
    @ResponseBody
    public List<OrderStatusDto> getOrderStatuses(){
        return orderService.getOrderStatuses();
    }
}
