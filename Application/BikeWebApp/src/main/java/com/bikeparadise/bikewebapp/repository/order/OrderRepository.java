package com.bikeparadise.bikewebapp.repository.order;

import com.bikeparadise.bikewebapp.model.order.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByClientId(Integer clientId);
}
