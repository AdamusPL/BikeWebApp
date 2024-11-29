package com.bikeparadise.bikewebapp.repository.user;

import com.bikeparadise.bikewebapp.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findUserByUsername(String username);
    List<User> findUserByUserData_UserEmail_Email(String email);
    List<User> findUserByUserData_UserPhoneNumber_PhoneNumber(String phoneNumber);
}
