package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Integer> {
}
