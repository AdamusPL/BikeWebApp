package com.bikeparadise.bikewebapp.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
    @Id
    Integer id;
    String firstName;
    String lastName;

    @OneToOne(mappedBy = "userData")
    User user;

    @OneToOne(mappedBy = "userData")
    UserContact userContact;

    @OneToOne(mappedBy = "userData")
    Client client;

    @OneToOne(mappedBy = "userData")
    ShopAssistant shopAssistant;

}
