package com.bikeparadise.bikewebapp.repository.user;

import com.bikeparadise.bikewebapp.model.user.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
}
