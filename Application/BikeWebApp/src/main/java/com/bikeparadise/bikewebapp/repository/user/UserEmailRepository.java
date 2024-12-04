package com.bikeparadise.bikewebapp.repository.user;

import com.bikeparadise.bikewebapp.model.user.UserEmail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserEmailRepository extends JpaRepository<UserEmail, Integer> {
    UserEmail findUserEmailByEmail(String email);
}
