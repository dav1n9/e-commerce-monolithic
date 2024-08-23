package com.example.fastbuymicroservices.domain.order.service;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import com.example.fastbuymicroservices.domain.item.repository.ItemRepository;
import com.example.fastbuymicroservices.domain.order.dto.CreateOrderItemDto;
import com.example.fastbuymicroservices.domain.order.dto.CreateOrderRequestDto;
import com.example.fastbuymicroservices.domain.order.dto.OrderResponseDto;
import com.example.fastbuymicroservices.domain.order.entity.Order;
import com.example.fastbuymicroservices.domain.order.entity.OrderStatus;
import com.example.fastbuymicroservices.domain.order.repository.OrderItemRepository;
import com.example.fastbuymicroservices.domain.order.repository.OrderRepository;
import com.example.fastbuymicroservices.domain.order.entity.OrderItem;
import com.example.fastbuymicroservices.domain.user.entity.User;
import com.example.fastbuymicroservices.global.common.EncryptionUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;
    private final EncryptionUtil encryptionUtil;

    public OrderResponseDto save(User user, CreateOrderRequestDto request) throws Exception {
        // order 생성
        Order order = orderRepository.save(Order.builder()
                .user(user)
                .address(encryptionUtil.decrypt(user.getAddress()))
                .status(OrderStatus.ORDER_COMPLETED)
                .build());

        // orderItem 생성 후 추가
        List<OrderItem> orderItems = new ArrayList<>();
        for (CreateOrderItemDto orderItem : request.getOrderItems()) {
            Item item = findItemById(orderItem.getItemId());
            item.removeStock(orderItem.getCount());
            orderItems.add(orderItem.toEntity(order, item));
        }
        orderItemRepository.saveAll(orderItems);
        order.setOrderItems(orderItems);

        return new OrderResponseDto(order);
    }

    public List<OrderResponseDto> findAll(User user) {
        List<Order> orders = orderRepository.findByUser(user);
        return orders.stream().map(OrderResponseDto::new).toList();
    }

    public OrderResponseDto cancelOrder(Long orderId) {
        return new OrderResponseDto();
    }

    public OrderResponseDto returnOrder(Long orderId) {
        return new OrderResponseDto();
    }

    private Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Not found item " + itemId));
    }
}
