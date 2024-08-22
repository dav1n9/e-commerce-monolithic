package com.example.fastbuymicroservices.domain.wishlist.repository;

import com.example.fastbuymicroservices.domain.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
}
