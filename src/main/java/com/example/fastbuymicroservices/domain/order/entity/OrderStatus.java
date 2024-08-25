package com.example.fastbuymicroservices.domain.order.entity;

public enum OrderStatus {
    ORDER_COMPLETED, // 주문완료
    SHIPPING,        // 배송중
    DELIVERED,       // 배송완료
    CANCELED,        // 취소완료
    RETURN_REQUESTED, // 반품신청
    RETURNED         // 반품완료
}
