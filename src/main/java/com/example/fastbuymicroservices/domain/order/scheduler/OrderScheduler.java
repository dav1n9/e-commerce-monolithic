package com.example.fastbuymicroservices.domain.order.scheduler;

import com.example.fastbuymicroservices.domain.order.entity.Order;
import com.example.fastbuymicroservices.domain.order.entity.OrderStatus;
import com.example.fastbuymicroservices.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 16 * * ?") // 매일 오후 4시 실행
    @Transactional
    public void updateOrderStatus() {
        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeDaysAgo4PM = now.minusDays(3).with(LocalTime.of(16, 0)); // 3일 전 오후 4시
        LocalDateTime twoDaysAgo4PM = now.minusDays(2).with(LocalTime.of(16, 0)); // 2일 전 오후 4시
        LocalDateTime yesterday4PM = now.minusDays(1).with(LocalTime.of(16, 0)); // 어제 오후 4시

        // 2일 전 오후 4시부터 어제 오후 4시까지 주문된 모든 주문 상태 'SHIPPING(배송중)'으로 업데이트
        List<Order> ordersToShip = orderRepository.findByStatusAndCreatedAtBetween(
                OrderStatus.ORDER_COMPLETED, twoDaysAgo4PM, yesterday4PM);
        for (Order order : ordersToShip) {
            order.setStatus(OrderStatus.SHIPPING);
        }
        orderRepository.saveAll(ordersToShip);

        // 3일 전 오후 4시부터 2일 전 오후 4시까지 주문된 모든 주문 상태 'DELIVERED(배송완료)'로 업데이트
        List<Order> ordersToComplete = orderRepository.findByStatusAndCreatedAtBetween(
                OrderStatus.SHIPPING, threeDaysAgo4PM, twoDaysAgo4PM);
        for (Order order : ordersToComplete) {
            order.setStatus(OrderStatus.DELIVERED);
        }
        orderRepository.saveAll(ordersToComplete);
    }
}
