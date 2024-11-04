package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.UserData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends JpaRepository<UserData, Integer> {
}
