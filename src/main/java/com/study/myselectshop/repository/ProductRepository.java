package com.study.myselectshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.myselectshop.entity.Product;
import com.study.myselectshop.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {
	List<Product> findAllByUser(User user);
}
