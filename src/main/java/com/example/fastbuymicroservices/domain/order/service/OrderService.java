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
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public OrderResponseDto cancelOrder(Long orderId) {
        Order order = findOrderById(orderId);
        if (order.getStatus() != OrderStatus.ORDER_COMPLETED && order.getStatus() != OrderStatus.SHIPPING ) {
            throw new IllegalArgumentException("주문 취소가 불가능 합니다.");
        }

        // 상품 재고 복구
        for (OrderItem orderItem : order.getOrderItems()) {
            Item item = orderItem.getItem();
            item.addStock(orderItem.getCount());
        }
        order.setStatus(OrderStatus.CANCELED);  // 취소완료 상태로 변경

        return new OrderResponseDto(order);
    }

    public OrderResponseDto returnOrder(Long orderId) {
        return new OrderResponseDto();
    }

    private Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new IllegalArgumentException("Not found item " + itemId));
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new IllegalArgumentException("Not found order " + orderId));
    }
}
