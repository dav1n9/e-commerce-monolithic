package com.example.fastbuymicroservices.domain.wishlist.entity;


import com.example.fastbuymicroservices.domain.user.entity.User;
import com.example.fastbuymicroservices.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wishlists")
public class Wishlist extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "wishlist")
    private List<WishlistItem> wishlistItems = new ArrayList<>();

    @Builder
    public Wishlist(String name, User user) {
        this.name = name;
        this.user = user;
    }
}
