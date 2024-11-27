package com.bikeparadise.bikewebapp.repository;

import com.bikeparadise.bikewebapp.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
