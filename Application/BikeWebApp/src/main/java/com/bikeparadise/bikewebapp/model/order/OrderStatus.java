package com.bikeparadise.bikewebapp.model.order;

import com.bikeparadise.bikewebapp.model.order.Order;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @Column(unique=true)
    String status;

    @OneToMany(mappedBy = "orderStatus")
    List<Order> order;

}
