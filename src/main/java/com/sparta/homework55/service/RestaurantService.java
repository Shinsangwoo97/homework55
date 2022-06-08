package com.sparta.homework55.service;

import com.sparta.homework55.dto.RestaurantDto;
import com.sparta.homework55.medel.Restaurant;
import com.sparta.homework55.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    //POST
    private final RestaurantRepository restaurantRepository;
    @Transactional
    public Restaurant addRestaurant(RestaurantDto restaurantDto){
        int minOrderPrice = restaurantDto.getMinOrderPrice();
        int deliveryFee = restaurantDto.getDeliveryFee();

        checkMinOrderPrice(minOrderPrice);

        checkDeliveryFee(deliveryFee);

        Restaurant restaurant = Restaurant.builder()
                .name(restaurantDto.getName())
                .minOrderPrice(minOrderPrice)
                .deliveryFee(deliveryFee)
                .build();

        restaurantRepository.save(restaurant);
        return restaurant;
    }

    private void checkMinOrderPrice(int minOrderPrice) {
        if(!(1000 <= minOrderPrice && minOrderPrice <= 100000)) {
            throw new IllegalArgumentException("허용값: 1,000원 ~ 100,000원 입력");
        }

        if(minOrderPrice % 100 > 0) {
            throw new IllegalArgumentException("100 원 단위로만 입력 가능");
        }
    }

    private void checkDeliveryFee(int deliveryFee) {
        if(0 > deliveryFee || deliveryFee > 10_000) {
            throw new IllegalArgumentException("허용값: 0원 ~ 10,000원");
        }

        if(deliveryFee % 500 > 0) {
            throw new IllegalArgumentException("500 원 단위로만 입력 가능");
        }
    }

    //GET
    public List<Restaurant> getRestaurant(){
        return restaurantRepository.findAll();
    }
}
