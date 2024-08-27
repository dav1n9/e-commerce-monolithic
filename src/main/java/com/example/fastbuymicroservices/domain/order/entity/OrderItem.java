package com.example.fastbuymicroservices.domain.order.entity;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import com.example.fastbuymicroservices.global.common.Timestamped;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem extends Timestamped {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_item_id")
    private Long id;

    private Integer orderPrice;

    private Integer count;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    @Builder
    public OrderItem(Integer orderPrice, Integer count, Order order, Item item) {
        this.orderPrice = orderPrice;
        this.count = count;
        this.order = order;
        this.item = item;
    }

    public Integer getTotalPrice() {
        return getOrderPrice() * getCount();
    }
}
