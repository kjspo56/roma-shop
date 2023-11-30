package com.roma.shop.backend.controller;

import com.roma.shop.backend.dto.OrderDto;
import com.roma.shop.backend.entity.Order;
import com.roma.shop.backend.repository.CartRepository;
import com.roma.shop.backend.repository.OrderRepository;
import com.roma.shop.backend.service.JwtService;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class OrderController {

    private final OrderRepository orderRepository;
    private final CartRepository cartRepository;
    private final JwtService jwtService;

    public OrderController(OrderRepository orderRepository, CartRepository cartRepository, JwtService jwtService) {
        this.orderRepository = orderRepository;
        this.cartRepository = cartRepository;
        this.jwtService = jwtService;
    }

    @GetMapping("/api/orders")
    public ResponseEntity getOrder(@CookieValue(value = "token", required = false) String token) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/api/orders")
    public ResponseEntity pushOrder(@RequestBody OrderDto dto, @CookieValue(value = "token", required = false) String token) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Order newOrder = new Order();

        newOrder.setMemberId(memberId);
        newOrder.setName(dto.getName());
        newOrder.setAddress(dto.getAddress());
        newOrder.setPayment(dto.getPayment());
        newOrder.setCardNumber(dto.getCardNumber());
        newOrder.setItems(dto.getItems());

        orderRepository.save(newOrder);
        cartRepository.deleteByMemberId(memberId);  //주문 이후 장바구니 비우기

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
