package com.bikeparadise.bikewebapp.repository.order;

import com.bikeparadise.bikewebapp.model.order.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderStatusRepository extends JpaRepository<OrderStatus, Integer> {
    List<OrderStatus> findByStatus(String status);
}
