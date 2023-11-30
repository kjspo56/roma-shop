package com.roma.shop.backend.repository;

import com.roma.shop.backend.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartRepository extends JpaRepository<Cart, Integer> {
    List<Cart> findByMemberId(int memberId);
    Cart findByMemberIdAndItemId(int memberId, int itemId);

    void deleteByMemberId(int memberId);
}
