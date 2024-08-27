package com.example.fastbuymicroservices.domain.item.dto;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import lombok.Getter;

@Getter
public class CreateItemRequestDto {

    private String name;
    private Integer price;
    private Integer stockQuantity;

    public Item toEntity() {
        return Item.builder()
                .name(name)
                .price(price)
                .stockQuantity(stockQuantity)
                .build();
    }
}