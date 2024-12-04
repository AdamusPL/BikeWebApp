package com.bikeparadise.bikewebapp.repository.bike;

import com.bikeparadise.bikewebapp.model.bike.Bike;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public interface BikeRepository extends JpaRepository<Bike, Integer> {
//    @PersistenceContext
//    EntityManager entityManager;
    @Query("SELECT MAX(b.price) FROM Bike b")
    BigDecimal findMaxPrice();

    @Query("SELECT MIN(b.price) FROM Bike b")
    BigDecimal findMinPrice();

//    public List<Bike> findBikesByCombinationsAndPrice(List<List<String>> combinations, BigDecimal minPrice, BigDecimal maxPrice) {
//        String query = "SELECT b FROM Bike b " +
//                "WHERE b.price BETWEEN :minPrice AND :maxPrice " +
//                "AND (";
//
//        for (int i = 0; i < combinations.size(); i++) {
//            if (i > 0) query += " AND ";
//            query += " :attributes" + i + " MEMBER OF b.attributes";
//        }
//
//        query += ")";
//
//        TypedQuery<Bike> query = entityManager.createQuery(query, Bike.class)
//                .setParameter("minPrice", minPrice)
//                .setParameter("maxPrice", maxPrice);
//
//        for (int i = 0; i < combinations.size(); i++) {
//            query.setParameter("attributes" + i, combinations.get(i));
//        }
//
//        return query.getResultList();
//    }

    List<Bike> findBikeByPriceBetween(BigDecimal minPrice, BigDecimal maxPrice);
}
