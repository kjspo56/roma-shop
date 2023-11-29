package com.roma.shop.backend.controller;

import com.roma.shop.backend.entity.Cart;
import com.roma.shop.backend.entity.Item;
import com.roma.shop.backend.repository.CartRepository;
import com.roma.shop.backend.repository.ItemRepository;
import com.roma.shop.backend.service.JwtService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
public class CartController {

    private final JwtService jwtService;
    private final CartRepository cartRepository;
    private final ItemRepository itemRepository;
    public CartController(JwtService jwtService, CartRepository cartRepository, ItemRepository itemRepository) {
        this.jwtService = jwtService;
        this.cartRepository = cartRepository;
        this.itemRepository = itemRepository;
    }

    //Todo: service로 분리

    @GetMapping("/api/cart/items")
    public ResponseEntity getCartItems(@CookieValue(value = "token", required = false) String token){

        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        List<Cart> carts = cartRepository.findByMemberId(memberId);
        List<Integer> itemIds = carts.stream().map(Cart::getItemId).toList();

        List<Item> items = itemRepository.findByIdIn(itemIds);

        return new ResponseEntity<>(items, HttpStatus.OK);

    }

    @PostMapping("/api/cart/items/{itemId}")
    public ResponseEntity pushCartItem(@PathVariable("itemId") int itemId,
                                   @CookieValue(value = "token", required = false) String token){
        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        if(cart == null){
            Cart newCart = new Cart();
            newCart.setMemberId(memberId);
            newCart.setItemId(itemId);
            cartRepository.save(newCart);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/api/cart/items/{itemId}")
    public ResponseEntity removeCartItem(@PathVariable("itemId") int itemId, @CookieValue(value = "token", required = false) String token) {

        if (!jwtService.isValid(token)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int memberId = jwtService.getId(token);
        Cart cart = cartRepository.findByMemberIdAndItemId(memberId, itemId);

        cartRepository.delete(cart);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
