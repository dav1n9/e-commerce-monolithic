package com.example.fastbuymicroservices.domain.order.dto;

import com.example.fastbuymicroservices.domain.order.entity.Order;
import com.example.fastbuymicroservices.domain.order.entity.OrderStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class OrderResponseDto {

    private Long id;
    private String address;
    private OrderStatus status;
    private List<OrderItemResponseDto> orderItems = new ArrayList<>();

    public OrderResponseDto(Order order) {
        this.id = order.getId();
        this.address = order.getAddress();
        this.status = order.getStatus();
        this.orderItems = order.getOrderItems().stream().map(OrderItemResponseDto::new).toList();
    }
}
