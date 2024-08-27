package com.example.fastbuymicroservices.domain.wishlist.service;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import com.example.fastbuymicroservices.domain.item.repository.ItemRepository;
import com.example.fastbuymicroservices.domain.user.entity.User;
import com.example.fastbuymicroservices.domain.wishlist.dto.*;
import com.example.fastbuymicroservices.domain.wishlist.entity.Wishlist;
import com.example.fastbuymicroservices.domain.wishlist.entity.WishlistItem;
import com.example.fastbuymicroservices.domain.wishlist.repository.WishlistItemRepository;
import com.example.fastbuymicroservices.domain.wishlist.repository.WishlistRepository;
import com.example.fastbuymicroservices.global.exception.BusinessException;
import com.example.fastbuymicroservices.global.exception.ErrorCode;
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

    public WishlistItemResponseDto addWishlistItem(Long wishlistId, AddWishlistItemRequestDto request) {
        Wishlist wishlist = findByWishlist(wishlistId);
        Item item = findByItem(request.getItemId());
        WishlistItem wishlistItem = WishlistItem.builder()
                .wishlist(wishlist)
                .item(item)
                .count(request.getCount())
                .build();

        wishlistItemRepository.save(wishlistItem);
        return new WishlistItemResponseDto(wishlistItem);
    }

    public WishlistResponseDto createWishlist(User user, CreateWishlistRequestDto request) {
        Wishlist wishlist = Wishlist.builder()
                .user(user)
                .name(request.getName())
                .build();
        wishlistRepository.save(wishlist);
        return new WishlistResponseDto(wishlist);
    }

    public WishlistResponseDto findWishlist(Long wishlistId) {
        Wishlist wishlist = findByWishlist(wishlistId);
        return new WishlistResponseDto(wishlist);
    }

    public List<WishlistResponseDto> findWishlist(User user) {
        return wishlistRepository.findByUser(user).stream().map(WishlistResponseDto::new).toList();
    }

    @Transactional
    public WishlistItemResponseDto updateItemCount(Long wishlistItemId, UpdateWishlistItemRequestDto request) {
        WishlistItem wishlistItem = findByWishlistItem(wishlistItemId);
        wishlistItem.setCount(request.getCount());
        return new WishlistItemResponseDto(wishlistItem);
    }

    public void deleteItem(Long wishlistItemId) {
        WishlistItem wishlistItem = findByWishlistItem(wishlistItemId);
        wishlistItemRepository.delete(wishlistItem);
    }

    private Wishlist findByWishlist(Long wishlistId) {
        return wishlistRepository.findById(wishlistId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WISHLIST_NOT_FOUND));
    }

    private Item findByItem(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
    }

    private WishlistItem findByWishlistItem(Long wishlistItemId) {
        return wishlistItemRepository.findById(wishlistItemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.WISHLIST_ITEM_NOT_FOUND));
    }
}
