package com.study.myselectshop.service;

import org.springframework.stereotype.Service;

import com.study.myselectshop.dto.ProductRequestDto.ProductRequestDto;
import com.study.myselectshop.dto.ProductResponseDto.ProductResponseDto;
import com.study.myselectshop.entity.Product;
import com.study.myselectshop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public ProductResponseDto createProduct(ProductRequestDto requestDto) {
		Product product = productRepository.save(new Product(requestDto));
		return new ProductResponseDto(product);
	}
}
