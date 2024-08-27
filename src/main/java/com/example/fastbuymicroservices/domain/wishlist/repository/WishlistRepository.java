package com.example.fastbuymicroservices.domain.wishlist.repository;

import com.example.fastbuymicroservices.domain.user.entity.User;
import com.example.fastbuymicroservices.domain.wishlist.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    List<Wishlist> findByUser(User user);
}
