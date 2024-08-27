package com.example.fastbuymicroservices.domain.order.scheduler;

import com.example.fastbuymicroservices.domain.item.entity.Item;
import com.example.fastbuymicroservices.domain.order.entity.Order;
import com.example.fastbuymicroservices.domain.order.entity.OrderItem;
import com.example.fastbuymicroservices.domain.order.entity.OrderStatus;
import com.example.fastbuymicroservices.domain.order.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class OrderScheduler {

    private final OrderRepository orderRepository;

    @Scheduled(cron = "0 0 2 * * ?")
    @Transactional
    public void updateOrderStatus() {
        LocalDate now = LocalDate.now();
        LocalDateTime startOfYesterday = now.minusDays(1).atStartOfDay();
        LocalDateTime endOfYesterday = now.minusDays(1).atTime(LocalTime.MAX);
        LocalDateTime startOfTwoDaysAgo = now.minusDays(2).atStartOfDay();
        LocalDateTime endOfTwoDaysAgo = now.minusDays(2).atTime(LocalTime.MAX);

        // 어제 자정부터 어제 23시 59분 59초까지 주문된 모든 주문 상태 SHIPPING(배송중)으로 업데이트
        List<Order> ordersToShip = orderRepository.findByStatusAndCreatedAtBetween(
                OrderStatus.ORDER_COMPLETED, startOfYesterday, endOfYesterday);
        for (Order order : ordersToShip) {
            order.setStatus(OrderStatus.SHIPPING);
        }
        orderRepository.saveAll(ordersToShip);

        // 2일 전 자정부터 2일 전 23시 59분 59초까지 주문된 모든 주문 상태 DELIVERED(배송완료)로 업데이트
        List<Order> ordersToComplete = orderRepository.findByStatusAndCreatedAtBetween(
                OrderStatus.SHIPPING, startOfTwoDaysAgo, endOfTwoDaysAgo);
        for (Order order : ordersToComplete) {
            order.setStatus(OrderStatus.DELIVERED);
        }
        orderRepository.saveAll(ordersToComplete);

        // 어제 자정부터 어제 23시 59분 59초까지 반품신청 된 모든 주문 상태 RETURNED(반품완료)로 업데이트
        List<Order> ordersToRequest = orderRepository.findByStatusAndUpdatedAtBetween(
                OrderStatus.RETURN_REQUESTED, startOfYesterday, endOfYesterday);
        for (Order order : ordersToRequest) {
            for (OrderItem orderItem : order.getOrderItems()) {
                Item item = orderItem.getItem();
                item.addStock(orderItem.getCount());
            }
            order.setStatus(OrderStatus.RETURNED);
        }
    }
}
