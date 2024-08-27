package com.example.fastbuymicroservices.domain.wishlist.entity;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import com.example.fastbuymicroservices.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "wishlist_items")
public class WishlistItem extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "wishlist_item_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private Integer count;

    @Builder
    public WishlistItem(Wishlist wishlist, Item item, Integer count) {
        this.wishlist = wishlist;
        this.item = item;
        this.count = count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
