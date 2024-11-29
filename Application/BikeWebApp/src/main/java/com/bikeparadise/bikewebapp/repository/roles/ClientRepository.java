package com.bikeparadise.bikewebapp.repository.roles;

import com.bikeparadise.bikewebapp.model.roles.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
}
