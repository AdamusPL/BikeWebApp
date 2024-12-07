package com.bikeparadise.bikewebapp.repository.bike;

import com.bikeparadise.bikewebapp.model.bike.Bike;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class BikeExtendedRepository {
    @PersistenceContext
    EntityManager entityManager;

    public List<Bike> findBikesByCombinationsAndPrice(List<List<String>> combinations, BigDecimal minPrice, BigDecimal maxPrice) {
        String query = "SELECT b FROM Bike b " +
                "WHERE b.price BETWEEN :minPrice AND :maxPrice " +
                "AND (";

        if(combinations.get(0).size() > 1){
            query += "(";
        }

        // Iterate over combinations and generate the appropriate condition
        int j = 0;
        for (int i = 0; i < combinations.size(); i++) {
            if (i > 0) {
                if(combinations.get(i).size() < 2){
                    query += " OR ";
                }
                else if (combinations.get(i - 1).get(j).equals(combinations.get(i).get(j))) {
                    query += ") OR (";
                    j++;
                }
                else{
                    query += " AND ";
                }
            }

            // Accessing bikeParameterAttribute and matching it with the combinations
            query += " EXISTS (SELECT 1 FROM b.bikeAttribute ba WHERE ba.bikeParameterAttribute.attribute IN :attributes" + i + ")";
        }

        query += ")";

        if(combinations.get(0).size() > 1){
            query += ")";
        }

        // Prepare the query with parameters
        TypedQuery<Bike> queryVar = entityManager.createQuery(query, Bike.class)
                .setParameter("minPrice", minPrice)
                .setParameter("maxPrice", maxPrice);

        // Set the combinations parameters
        for (int i = 0; i < combinations.size(); i++) {
            queryVar.setParameter("attributes" + i, combinations.get(i));
        }

        return queryVar.getResultList();
    }

}
