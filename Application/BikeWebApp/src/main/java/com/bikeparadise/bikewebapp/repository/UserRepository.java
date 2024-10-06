package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUserByUsername(String username);
    List<User> findUserByUserData_UserContact_Email(String username);
    List<User> findUserByUserData_UserContact_PhoneNumber(String phoneNumber);
}
