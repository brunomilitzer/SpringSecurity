package com.brunomilitzer.springsecurity.repositories;

import com.brunomilitzer.springsecurity.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
