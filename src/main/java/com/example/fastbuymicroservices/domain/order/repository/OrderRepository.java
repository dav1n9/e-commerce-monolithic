package com.example.fastbuymicroservices.domain.order.repository;

import com.example.fastbuymicroservices.domain.order.entity.Order;
import com.example.fastbuymicroservices.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);
}
