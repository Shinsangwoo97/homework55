package com.sparta.homework55.dto;

import com.sparta.homework55.medel.OrderSheet;
import lombok.Getter;

import java.util.List;

@Getter
public class OrdersRequestDto {
    private Long restaurantId;
    private List<OrderSheet> foods;
}
