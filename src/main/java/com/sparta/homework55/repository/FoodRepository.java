package com.sparta.homework55.repository;


import com.sparta.homework55.medel.Food;
import com.sparta.homework55.medel.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Long> {
    List<Food> findFoodsByRestaurant(Restaurant restaurant);
    Optional<Food> findFoodByRestaurantAndName(Restaurant restaurant, String foodName);
}
