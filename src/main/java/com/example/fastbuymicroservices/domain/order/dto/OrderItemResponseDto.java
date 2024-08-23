package com.example.fastbuymicroservices.domain.order.dto;

import com.example.fastbuymicroservices.domain.order.entity.OrderItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class OrderItemResponseDto {
    private Long itemId;
    private String itemName;
    private Integer orderPrice;
    private Integer count;

    public OrderItemResponseDto(OrderItem orderItem) {
        this.itemId = orderItem.getItem().getId();
        this.itemName = orderItem.getItem().getName();
        this.orderPrice = orderItem.getOrderPrice();
        this.count = orderItem.getCount();
    }
}
