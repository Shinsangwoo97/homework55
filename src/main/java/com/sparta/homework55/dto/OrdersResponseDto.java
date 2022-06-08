package com.sparta.homework55.dto;

import com.sparta.homework55.medel.Orders;
import lombok.Getter;

import java.util.List;


@Getter
public class OrdersResponseDto {
    private String restaurantName;
    private List<FoodResponseDto> foods;
    private int deliveryFee;
    private int totalPrice;

    public OrdersResponseDto(Orders orders, int deliveryFee, List<FoodResponseDto>foods){
        this.restaurantName = orders.getRestaurantName();
        this.foods = foods;
        this.deliveryFee = deliveryFee;
        this.totalPrice = orders.getTotalPrice();
    }
}
