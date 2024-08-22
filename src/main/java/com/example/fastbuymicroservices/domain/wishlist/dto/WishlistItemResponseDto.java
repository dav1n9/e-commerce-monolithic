package com.example.fastbuymicroservices.domain.wishlist.dto;

import com.example.fastbuymicroservices.domain.wishlist.entity.WishlistItem;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class WishlistItemResponseDto {

    private Long id;
    private String itemName;
    private Integer itemPrice;
    private Integer count;

    public WishlistItemResponseDto(WishlistItem wishlistItem) {
        this.id = wishlistItem.getId();
        this.itemName = wishlistItem.getItem().getName();
        this.itemPrice = wishlistItem.getItem().getPrice();
        this.count = wishlistItem.getCount();
    }
}