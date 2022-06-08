package com.sparta.homework55.repository;

import com.sparta.homework55.medel.OrderSheet;
import com.sparta.homework55.medel.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderSheetRepository extends JpaRepository<OrderSheet, Long> {
    List<OrderSheet> findOrderItemsByOrders(Orders orders);
}
