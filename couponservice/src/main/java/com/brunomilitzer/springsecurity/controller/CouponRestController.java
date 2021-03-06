package com.brunomilitzer.springsecurity.controller;

import com.brunomilitzer.springsecurity.model.Coupon;
import com.brunomilitzer.springsecurity.repositories.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/couponapi")
@CrossOrigin
public class CouponRestController {

    @Autowired
    private CouponRepository repository;

    @PostMapping(value = "/coupons")
    @PreAuthorize("hasRole('ADMIN')")
    public Coupon create(@RequestBody Coupon coupon) {

        return repository.save(coupon);
    }

    @GetMapping(value = "/coupons/{code}")
    //@PostAuthorize("returnObject.discount < 20")
    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    public Coupon getCoupon(@PathVariable("code") String code) {

        return repository.findByCode(code);
    }
}
