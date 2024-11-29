package com.bikeparadise.bikewebapp.model.roles;

import com.bikeparadise.bikewebapp.model.review.Review;
import com.bikeparadise.bikewebapp.model.order.Order;
import com.bikeparadise.bikewebapp.model.user.UserData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;

    @OneToMany(mappedBy = "client")
    private List<Review> review;

    @OneToMany(mappedBy = "client")
    private List<Order> order;

    public Client(UserData userData){
        this.userData = userData;
    }
}
