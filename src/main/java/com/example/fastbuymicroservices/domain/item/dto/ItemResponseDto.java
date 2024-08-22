package com.example.fastbuymicroservices.domain.item.dto;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import lombok.Getter;

@Getter
public class ItemResponseDto {

    private final Long id;
    private final String name;
    private final Integer price;
    private final Integer stockQuantity;

    public ItemResponseDto(Item item) {
        this.id = item.getId();
        this.name = item.getName();
        this.price = item.getPrice();
        this.stockQuantity = item.getStockQuantity();
    }
}
