package com.example.fastbuymicroservices.domain.wishlist.service;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import com.example.fastbuymicroservices.domain.item.repository.ItemRepository;
import com.example.fastbuymicroservices.domain.wishlist.dto.AddWishlistItemRequestDto;
import com.example.fastbuymicroservices.domain.wishlist.dto.UpdateWishlistItemRequestDto;
import com.example.fastbuymicroservices.domain.wishlist.dto.WishlistItemResponseDto;
import com.example.fastbuymicroservices.domain.wishlist.entity.Wishlist;
import com.example.fastbuymicroservices.domain.wishlist.entity.WishlistItem;
import com.example.fastbuymicroservices.domain.wishlist.repository.WishlistItemRepository;
import com.example.fastbuymicroservices.domain.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final ItemRepository itemRepository;
    private final WishlistItemRepository wishlistItemRepository;

    public void addWishlistItem(Long wishlistId, AddWishlistItemRequestDto request) {
        Wishlist wishlist = findByWishlist(wishlistId);
        Item item = findByItem(request.getItemId());
        WishlistItem wishlistItem = WishlistItem.builder()
                .wishlist(wishlist)
                .item(item)
                .count(request.getCount())
                .build();

        wishlistItemRepository.save(wishlistItem);
    }

    public List<WishlistItemResponseDto> findAll(Long wishlistId) {
        Wishlist wishlist = findByWishlist(wishlistId);
        List<WishlistItem> wishlistItems = wishlistItemRepository.findByWishlist(wishlist);

        return wishlistItems.stream().map(WishlistItemResponseDto::new).toList();
    }

    @Transactional
    public void updateItemCount(Long wishlistId, Long itemId, UpdateWishlistItemRequestDto request) {
        Wishlist wishlist = findByWishlist(wishlistId);
        Item item = findByItem(itemId);

        WishlistItem wishlistItem = findByWishlistItem(wishlist, item);
        wishlistItem.setCount(request.getCount());
    }

    public void deleteItem(Long wishlistId, Long itemId) {
        Wishlist wishlist = findByWishlist(wishlistId);
        Item item = findByItem(itemId);

        WishlistItem wishlistItem = findByWishlistItem(wishlist, item);
        wishlistItemRepository.delete(wishlistItem);
    }

    private Wishlist findByWishlist(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new NullPointerException("Not found wishlist " + wishlistId));
    }

    private Item findByItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new NullPointerException("Not found item " + itemId));
    }

    private WishlistItem findByWishlistItem(Wishlist wishlist, Item item) {
        return wishlistItemRepository.findByWishlistAndItem(wishlist, item)
                .orElseThrow(() -> new NullPointerException("Not found wishlistItem"));
    }
}
