package com.sparta.homework55.controller;


import com.sparta.homework55.dto.RestaurantDto;
import com.sparta.homework55.medel.Restaurant;
import com.sparta.homework55.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurant/register")
    public Restaurant addRestaurant(
            @RequestBody RestaurantDto restaurantDto
            ){
        return restaurantService.addRestaurant(restaurantDto);
    }



    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurant(){
        return restaurantService.getRestaurant();
    }
}
