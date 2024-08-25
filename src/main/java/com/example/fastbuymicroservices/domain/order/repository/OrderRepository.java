package com.example.fastbuymicroservices.domain.order.repository;

import com.example.fastbuymicroservices.domain.order.entity.Order;
import com.example.fastbuymicroservices.domain.order.entity.OrderStatus;
import com.example.fastbuymicroservices.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
    List<Order> findByStatusAndCreatedAtBetween(OrderStatus status, LocalDateTime start, LocalDateTime end);
    List<Order> findByStatusAndUpdatedAtBetween(OrderStatus status, LocalDateTime start, LocalDateTime end);
}
