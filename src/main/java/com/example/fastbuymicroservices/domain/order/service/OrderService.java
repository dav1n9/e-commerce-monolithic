package com.example.fastbuymicroservices.domain.order.service;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import com.example.fastbuymicroservices.domain.item.repository.ItemRepository;
import com.example.fastbuymicroservices.domain.order.dto.CreateOrderItemDto;
import com.example.fastbuymicroservices.domain.order.dto.CreateOrderRequestDto;
import com.example.fastbuymicroservices.domain.order.dto.OrderResponseDto;
import com.example.fastbuymicroservices.domain.order.entity.Order;
import com.example.fastbuymicroservices.domain.order.entity.OrderItem;
import com.example.fastbuymicroservices.domain.order.entity.OrderStatus;
import com.example.fastbuymicroservices.domain.order.repository.OrderItemRepository;
import com.example.fastbuymicroservices.domain.order.repository.OrderRepository;
import com.example.fastbuymicroservices.domain.user.entity.User;
import com.example.fastbuymicroservices.global.exception.BusinessException;
import com.example.fastbuymicroservices.global.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final ItemRepository itemRepository;

    @Transactional
    public OrderResponseDto save(User user, CreateOrderRequestDto request) {
        // order 생성
        Order order = orderRepository.save(Order.builder()
                .user(user)
                .address(user.getAddress())
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
    public OrderResponseDto cancelOrder(User user, Long orderId) {
        Order order = findOrderById(orderId);

        if (order.getUser() != user)
            throw new BusinessException(ErrorCode.NOT_USER_ORDER);

        if (order.getStatus() != OrderStatus.ORDER_COMPLETED && order.getStatus() != OrderStatus.SHIPPING) {
            throw new BusinessException(ErrorCode.ORDER_CANCELLATION_NOT_ALLOWED);
        }

        for (OrderItem orderItem : order.getOrderItems()) {
            Item item = orderItem.getItem();
            item.addStock(orderItem.getCount());
        }

        order.setStatus(OrderStatus.CANCELED);
        return new OrderResponseDto(order);
    }

    @Transactional
    public OrderResponseDto returnOrder(User user, Long orderId) {
        Order order = findOrderById(orderId);

        if (order.getUser() != user)
            throw new BusinessException(ErrorCode.NOT_USER_ORDER);

        if (order.getStatus() != OrderStatus.DELIVERED) {
            throw new BusinessException(ErrorCode.ORDER_NOT_DELIVERED);
        }

        LocalDateTime oneDayAgo = LocalDateTime.now().minusDays(1);
        if (order.getUpdatedAt().isBefore(oneDayAgo)) {
            throw new BusinessException(ErrorCode.RETURN_PERIOD_EXPIRED);
        }

        order.setStatus(OrderStatus.RETURN_REQUESTED);
        return new OrderResponseDto(order);
    }

    private Item findItemById(Long itemId) {
        return itemRepository.findById(itemId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ITEM_NOT_FOUND));
    }

    private Order findOrderById(Long orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new BusinessException(ErrorCode.ORDER_NOT_FOUND));
    }
}
