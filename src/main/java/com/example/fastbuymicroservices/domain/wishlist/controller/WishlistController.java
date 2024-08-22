package com.example.fastbuymicroservices.domain.wishlist.controller;

import com.example.fastbuymicroservices.domain.wishlist.dto.AddWishlistItemRequestDto;
import com.example.fastbuymicroservices.domain.wishlist.dto.UpdateWishlistItemRequestDto;
import com.example.fastbuymicroservices.domain.wishlist.dto.WishlistItemResponseDto;
import com.example.fastbuymicroservices.domain.wishlist.service.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{wishlistId}")
    public ResponseEntity<Void> addWishlistItem(@PathVariable Long wishlistId,
                                                @RequestBody AddWishlistItemRequestDto request) {
        wishlistService.addWishlistItem(wishlistId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<List<WishlistItemResponseDto>> findAllItem(@PathVariable Long wishlistId) {
        return ResponseEntity.ok().body(wishlistService.findAll(wishlistId));
    }

    @PatchMapping("/{wishlistId}/item/{itemId}")
    public ResponseEntity<Void> updateItemCount(@PathVariable Long wishlistId,
                                                @PathVariable Long itemId,
                                                @RequestBody UpdateWishlistItemRequestDto request) {
        wishlistService.updateItemCount(wishlistId, itemId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{wishlistId}/item/{itemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long wishlistId,
                                           @PathVariable Long itemId) {
        wishlistService.deleteItem(wishlistId, itemId);
        return ResponseEntity.ok().build();
    }
}
