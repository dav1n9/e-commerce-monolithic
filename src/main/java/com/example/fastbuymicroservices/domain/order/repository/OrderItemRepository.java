package com.example.fastbuymicroservices.domain.order.repository;

import com.example.fastbuymicroservices.domain.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
