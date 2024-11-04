package com.study.myselectshop.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.myselectshop.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
