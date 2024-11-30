package com.bikeparadise.bikewebapp.model.user;

import com.bikeparadise.bikewebapp.model.roles.Client;
import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class UserData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String firstName;
    String lastName;

    @OneToOne(mappedBy = "userData", cascade = CascadeType.ALL)
    User user;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL)
    List<UserPhoneNumber> userPhoneNumber;

    @OneToMany(mappedBy = "userData", cascade = CascadeType.ALL)
    List<UserEmail> userEmail;

    @OneToOne(mappedBy = "userData", cascade = CascadeType.ALL)
    Client client;

    @OneToOne(mappedBy = "userData", cascade = CascadeType.ALL)
    ShopAssistant shopAssistant;

    public UserData(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
