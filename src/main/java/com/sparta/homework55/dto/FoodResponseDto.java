package com.sparta.homework55.dto;

import com.sparta.homework55.medel.OrderSheet;
import lombok.Getter;


@Getter
public class FoodResponseDto {
    private String name;
    private int quantity;
    private int price;

    public FoodResponseDto(OrderSheet orderItem) {
        this.name = orderItem.getName();
        this.quantity = orderItem.getQuantity();
        this.price = orderItem.getPrice();
    }
}
