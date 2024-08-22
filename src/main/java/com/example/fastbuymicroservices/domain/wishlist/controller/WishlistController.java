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

    @PatchMapping("/item/{wishlistItemId}")
    public ResponseEntity<Void> updateItemCount (@PathVariable Long wishlistItemId,
                                                @RequestBody UpdateWishlistItemRequestDto request) {
        wishlistService.updateItemCount(wishlistItemId, request);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/item/{wishlistItemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long wishlistItemId) {
        wishlistService.deleteItem(wishlistItemId);
        return ResponseEntity.ok().build();
    }
}
