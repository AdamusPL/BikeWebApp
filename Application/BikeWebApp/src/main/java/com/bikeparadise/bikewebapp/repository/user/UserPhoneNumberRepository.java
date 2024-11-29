package com.bikeparadise.bikewebapp.repository.user;

import com.bikeparadise.bikewebapp.model.user.UserPhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserPhoneNumberRepository extends JpaRepository<UserPhoneNumber, Integer> {
}
