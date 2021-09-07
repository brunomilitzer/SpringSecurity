package com.brunomilitzer.springsecurity.repositories;

import com.brunomilitzer.springsecurity.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

    Coupon findByCode(String code);
}
