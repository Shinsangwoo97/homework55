package com.sparta.homework55.service;

import com.sparta.homework55.dto.FoodResponseDto;
import com.sparta.homework55.dto.OrdersRequestDto;
import com.sparta.homework55.dto.OrdersResponseDto;
import com.sparta.homework55.medel.Food;
import com.sparta.homework55.medel.OrderSheet;
import com.sparta.homework55.medel.Orders;
import com.sparta.homework55.medel.Restaurant;
import com.sparta.homework55.repository.FoodRepository;
import com.sparta.homework55.repository.OrderRepository;
import com.sparta.homework55.repository.OrderSheetRepository;
import com.sparta.homework55.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final FoodRepository foodRepository;
    private final OrderRepository orderRepository;
    private final OrderSheetRepository orderSheetRepository;
    private final RestaurantRepository restaurantRepository;

    @Transactional
    public OrdersResponseDto order(OrdersRequestDto ordersRequestDto) {
        Restaurant restaurant = getRestaurant(ordersRequestDto);

        int totalPrice = 0;
        List<FoodResponseDto> foodsResponseDtoList = new ArrayList<>();
        List<OrderSheet> orderSheets = ordersRequestDto.getFoods();
        List<OrderSheet> orderSheetList = new ArrayList<>();
        for (OrderSheet tempOrderSheet : orderSheets) {

            int quantity = tempOrderSheet.getQuantity();
            if (quantity < 1 || quantity > 100) {
                throw new IllegalArgumentException("음식 주문가능수량은 1 ~ 100까지 입니다. ");
            }
            Food food = getFood(tempOrderSheet);

            OrderSheet orderSheet = OrderSheet.builder()
                    .quantity(tempOrderSheet.getQuantity())
                    .name(food.getName())
                    .price(food.getPrice() * quantity)
                    .food(food)
                    .build();
            orderSheetRepository.save(orderSheet);
            FoodResponseDto foodResponseDto = new FoodResponseDto(orderSheet);
            foodsResponseDtoList.add(foodResponseDto);
            totalPrice += food.getPrice() * quantity;
            orderSheetList.add(orderSheet);
        }
        if (totalPrice < restaurant.getMinOrderPrice()) {
            throw new IllegalStateException("최소 주문금액을 맞추지못했습니다.");
        }

        int deliveryFee = restaurant.getDeliveryFee();
        totalPrice += deliveryFee;
        Orders orders = Orders.builder()
                .restaurantName(restaurant.getName())
                .totalPrice(totalPrice)
                .foods(orderSheetList)
                .build();
        orderRepository.save(orders);
        OrdersResponseDto ordersResponseDto = new OrdersResponseDto(orders, deliveryFee, foodsResponseDtoList);
        return ordersResponseDto;
    }

   //음식점 이름같은지 확인
    private Restaurant getRestaurant(OrdersRequestDto ordersRequestDto) {
        Restaurant restaurant = restaurantRepository.findById(ordersRequestDto.getRestaurantId())
                .orElseThrow(
                        ()-> new NullPointerException("레스토랑이 없습니다")
                );
        return restaurant;
    }

        private Food getFood(OrderSheet tempOrderSheet) {
            return foodRepository.findById(tempOrderSheet.getId())
                    .orElseThrow(() -> new NullPointerException("해당 음식이 없습니다."));
        }
        @Transactional
        public List<OrdersResponseDto> findAllOrder() {
            List<OrdersResponseDto> ordersResponseDtoList = new ArrayList<>();

            List<Orders> ordersList = orderRepository.findAll();

            for (Orders orders : ordersList) {
                int deliveryFee = restaurantRepository.findByName(orders.getRestaurantName()).getDeliveryFee();
                List<FoodResponseDto> foodResponseDtoList = new ArrayList<>();


                List<OrderSheet> orderSheetList  = orderSheetRepository.findOrderItemsByOrders(orders);
                for (OrderSheet orderSheet : orderSheetList) {
                    FoodResponseDto foodResponseDto = new FoodResponseDto(orderSheet);
                    foodResponseDtoList.add(foodResponseDto);
                }

                OrdersResponseDto ordersResponseDto = new OrdersResponseDto(orders, deliveryFee, foodResponseDtoList);
                ordersResponseDtoList.add(ordersResponseDto);
            }

            return ordersResponseDtoList;
        }
}
