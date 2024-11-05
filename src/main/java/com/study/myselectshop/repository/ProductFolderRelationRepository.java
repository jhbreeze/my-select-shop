package com.study.myselectshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.myselectshop.entity.Folder;
import com.study.myselectshop.entity.Product;
import com.study.myselectshop.entity.ProductFolderRelation;

public interface ProductFolderRelationRepository extends JpaRepository<ProductFolderRelation, Long> {
	Optional<ProductFolderRelation> findByProductAndFolder(Product product, Folder folder);
}
