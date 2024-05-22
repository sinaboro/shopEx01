package com.shop.repository;

import com.shop.entity.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRopository extends JpaRepository<Cart,Long> {
    Cart findByMemberId(Long memberId);
}
