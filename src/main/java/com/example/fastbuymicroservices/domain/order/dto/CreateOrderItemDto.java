package com.example.fastbuymicroservices.domain.order.dto;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import com.example.fastbuymicroservices.domain.order.entity.Order;
import com.example.fastbuymicroservices.domain.order.entity.OrderItem;
import lombok.Getter;

@Getter
public class CreateOrderItemDto {
    private Long itemId;
    private Integer count;

    public OrderItem toEntity(Order order, Item item) {
        return OrderItem.builder()
                .order(order)
                .item(item)
                .orderPrice(item.getPrice())
                .count(count)
                .build();
    }
}
