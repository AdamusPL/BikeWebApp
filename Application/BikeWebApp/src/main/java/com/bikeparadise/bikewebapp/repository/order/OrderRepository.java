package com.bikeparadise.bikewebapp.repository.order;

import com.bikeparadise.bikewebapp.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByClientId(Integer clientId);
}
