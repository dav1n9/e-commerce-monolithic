package com.example.fastbuymicroservices.domain.wishlist.controller;

import com.example.fastbuymicroservices.domain.wishlist.dto.*;
import com.example.fastbuymicroservices.domain.wishlist.service.WishlistService;
import com.example.fastbuymicroservices.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/wishlists")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/{wishlistId}")
    public ResponseEntity<WishlistItemResponseDto> addWishlistItem(@PathVariable Long wishlistId,
                                                @RequestBody AddWishlistItemRequestDto request) {
        return ResponseEntity.ok().body(wishlistService.addWishlistItem(wishlistId, request));
    }

    @PostMapping()
    public ResponseEntity<WishlistResponseDto> createWishlist(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                              @RequestBody CreateWishlistRequestDto request) {
        return ResponseEntity.ok().body(wishlistService.createWishlist(userDetails.getUser(), request));
    }

    @GetMapping("/{wishlistId}")
    public ResponseEntity<WishlistResponseDto> findWishlist(@PathVariable Long wishlistId) {
        return ResponseEntity.ok().body(wishlistService.findWishlist(wishlistId));
    }

    @GetMapping
    public ResponseEntity<List<WishlistResponseDto>> findAllWishlist(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(wishlistService.findWishlist(userDetails.getUser()));
    }

    @PatchMapping("/items/{wishlistItemId}")
    public ResponseEntity<WishlistItemResponseDto> updateItemCount (@PathVariable Long wishlistItemId,
                                                @RequestBody UpdateWishlistItemRequestDto request) {
        return ResponseEntity.ok().body(wishlistService.updateItemCount(wishlistItemId, request));
    }

    @DeleteMapping("/items/{wishlistItemId}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long wishlistItemId) {
        wishlistService.deleteItem(wishlistItemId);
        return ResponseEntity.ok().build();
    }
}
