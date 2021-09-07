package com.brunomilitzer.springsecurity.controllers;

import com.brunomilitzer.springsecurity.dto.Coupon;
import com.brunomilitzer.springsecurity.model.Product;
import com.brunomilitzer.springsecurity.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping(value = "/productapi")
public class ProductController {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${couponService.url}")
    private String couponServiceUrl;

    @PostMapping(value = "/product")
    public Product create(@RequestBody Product product) {

        Coupon coupon = restTemplate.getForObject(couponServiceUrl + product.getCouponCode(), Coupon.class);

        if (coupon != null) {
            product.setPrice(product.getPrice().subtract(coupon.getDiscount()));
        }

        return repository.save(product);
    }

    @GetMapping(value = "/product/{id}")
    public Product getProduct(@PathVariable("id") Long id) {

        return repository.findById(id).get();
    }
}
