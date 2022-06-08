package com.sparta.homework55.controller;


import com.sparta.homework55.dto.FoodRequestDto;
import com.sparta.homework55.medel.Food;
import com.sparta.homework55.service.FoodService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/restaurant/{restaurantId}/food/register")
    public void addRestaurantFood(
            @PathVariable Long restaurantId,
            @RequestBody List<FoodRequestDto> requestDtoList
    ){
        foodService.addRestaurantFoods(restaurantId, requestDtoList);
    }

    @GetMapping("/restaurant/{restaurantId}/foods")
    public List<Food> findAllRestaurantFoods(
            @PathVariable Long restaurantId
    ) {
        return foodService.findAllRestaurantFoods(restaurantId);
    }
}
