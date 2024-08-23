package com.example.fastbuymicroservices.domain.order.controller;

import com.example.fastbuymicroservices.domain.order.dto.CreateOrderRequestDto;
import com.example.fastbuymicroservices.domain.order.dto.OrderResponseDto;
import com.example.fastbuymicroservices.domain.order.service.OrderService;
import com.example.fastbuymicroservices.global.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponseDto> createOrder(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                                        @RequestBody CreateOrderRequestDto request) throws Exception {
        return ResponseEntity.ok().body(orderService.save(userDetails.getUser(), request));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponseDto>> findAllOrder(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(orderService.findAll(userDetails.getUser()));
    }

    @PatchMapping("/{orderId}/cancel")
    public ResponseEntity<OrderResponseDto> cancelOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderService.cancelOrder(orderId));
    }

    @PatchMapping("/{orderId}/return")
    public ResponseEntity<OrderResponseDto> returnOrder(@PathVariable Long orderId) {
        return ResponseEntity.ok().body(orderService.returnOrder(orderId));
    }
}
