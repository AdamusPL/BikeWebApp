package com.bikeparadise.bikewebapp.repository.roles;

import com.bikeparadise.bikewebapp.model.roles.ShopAssistant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShopAssistantRepository extends JpaRepository<ShopAssistant, Integer> {
}
