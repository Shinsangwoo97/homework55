package com.sparta.homework55.repository;

import com.sparta.homework55.medel.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders,Long> {
}
