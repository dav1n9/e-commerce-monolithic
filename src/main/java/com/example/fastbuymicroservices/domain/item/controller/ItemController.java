package com.example.fastbuymicroservices.domain.item.controller;

import com.example.fastbuymicroservices.domain.item.dto.CreateItemRequestDto;
import com.example.fastbuymicroservices.domain.item.dto.ItemResponseDto;
import com.example.fastbuymicroservices.domain.item.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/item")
public class ItemController {

    private final ItemService itemService;

    @PostMapping
    public ResponseEntity<ItemResponseDto> createItem(@RequestBody CreateItemRequestDto request) {
        return ResponseEntity.ok().body(itemService.save(request));
    }

    @GetMapping
    public ResponseEntity<List<ItemResponseDto>> findAllItem() {
        return ResponseEntity.ok().body(itemService.findAll());
    }

    @GetMapping("/{itemId}")
    public ResponseEntity<ItemResponseDto> findItemById(@PathVariable Long itemId) {
        return ResponseEntity.ok().body(itemService.findById(itemId));
    }
}
