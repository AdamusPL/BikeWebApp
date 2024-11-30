package com.bikeparadise.bikewebapp.model.roles;

import com.bikeparadise.bikewebapp.model.bike.Bike;
import com.bikeparadise.bikewebapp.model.part.Part;
import com.bikeparadise.bikewebapp.model.user.UserData;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ShopAssistant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    @OneToOne
    @JoinColumn(name = "UserDataId")
    UserData userData;

    @OneToMany(mappedBy = "shopAssistant")
    List<Part> part;

    @OneToMany(mappedBy = "shopAssistant")
    List<Bike> bike;

    public ShopAssistant(UserData userData){
        this.userData = userData;
    }

}
