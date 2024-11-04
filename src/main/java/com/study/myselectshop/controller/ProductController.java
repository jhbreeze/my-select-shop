package com.study.myselectshop.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.myselectshop.dto.ProductRequestDto.ProductRequestDto;
import com.study.myselectshop.dto.ProductResponseDto.ProductResponseDto;
import com.study.myselectshop.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

	private final ProductService productService;

	// 관심상품 등록
	@PostMapping("/products")
	public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto) {
		return productService.createProduct(requestDto);
	}

}
