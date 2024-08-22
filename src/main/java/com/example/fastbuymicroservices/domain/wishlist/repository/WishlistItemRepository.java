package com.example.fastbuymicroservices.domain.wishlist.repository;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import com.example.fastbuymicroservices.domain.wishlist.entity.Wishlist;
import com.example.fastbuymicroservices.domain.wishlist.entity.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface WishlistItemRepository extends JpaRepository<WishlistItem, Long> {
    List<WishlistItem> findByWishlist(Wishlist wishlist);

    Optional<WishlistItem> findByWishlistAndItem(Wishlist wishlist, Item item);
}
