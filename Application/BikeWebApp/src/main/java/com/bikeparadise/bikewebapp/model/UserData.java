package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String firstName;
    String lastName;

    @OneToOne(mappedBy = "userData")
    User user;

    @OneToMany(mappedBy = "userData")
    List<UserContact> userContact;

    @OneToOne(mappedBy = "userData")
    Client client;

    @OneToOne(mappedBy = "userData")
    ShopAssistant shopAssistant;

}
