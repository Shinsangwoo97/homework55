package com.sparta.homework55.service;

import com.sparta.homework55.dto.FoodRequestDto;
import com.sparta.homework55.medel.Food;
import com.sparta.homework55.medel.Restaurant;
import com.sparta.homework55.repository.FoodRepository;
import com.sparta.homework55.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FoodService {
        private final FoodRepository foodRepository;
        private final RestaurantRepository restaurantRepository;

    @Transactional
    public void addRestaurantFoods(
             Long restaurantId,
             List<FoodRequestDto> responseDtoList
    ) {
        Optional<Restaurant> foundRestaurant = restaurantRepository.findById(restaurantId);

        checkRestaurant(foundRestaurant);
        Restaurant restaurant = foundRestaurant.get();

        for (FoodRequestDto requestDto : responseDtoList) {
            String foodName = requestDto.getName();
            int foodPrice = requestDto.getPrice();

            checkDuplicateRestaurantFood(restaurant, foodName);

            checkFoodPrice(foodPrice);

            Food food = Food.builder()
                    .name(foodName)
                    .price(foodPrice)
                    .restaurant(restaurant)
                    .build();

            foodRepository.save(food);
        }
    }

    private void checkRestaurant(Optional<Restaurant> foundRestaurant) {
        if(!foundRestaurant.isPresent())
            throw new IllegalStateException("레스토랑이 없습니다.");
    }

    private void checkDuplicateRestaurantFood(Restaurant restaurant, String foodName) {
        Optional<Food> found = foodRepository.findFoodByRestaurantAndName(restaurant, foodName);
        if(found.isPresent())
            throw new IllegalStateException("같은 음식점 내에서는 음식 이름이 중복될 수 없음");
    }

    private void checkFoodPrice(int foodPrice) {
        if (foodPrice < 100)
            throw new IllegalStateException("음식값이 100원 미만입니다");
        if (foodPrice > 1000000)
            throw new IllegalStateException("음식값이 1,000,000원 을 초과했습니다");
        if (foodPrice % 100 > 0)
            throw new IllegalArgumentException("100원 단위로 입력해주세요");
        }

        @Transactional
        public List<Food> findAllRestaurantFoods (Long restaurantId){
            Restaurant restaurant = restaurantRepository.findById(restaurantId)
                    .orElseThrow(
                            () -> new NullPointerException("레스토랑이 없습니다."));

            return foodRepository.findFoodsByRestaurant(restaurant);
        }
    }