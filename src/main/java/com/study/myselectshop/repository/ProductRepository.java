package com.study.myselectshop.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.study.myselectshop.entity.Product;
import com.study.myselectshop.entity.User;

public interface ProductRepository extends JpaRepository<Product, Long> {

	Page<Product> findAllByUser(User user, Pageable pageable);

	Page<Product> findAllByUserAndProductFolders_FolderId(User user, Long folderId, Pageable pageable);

}
