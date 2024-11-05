package com.study.myselectshop.controller;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.myselectshop.dto.ProductMyPriceRequestDto;
import com.study.myselectshop.dto.ProductRequestDto;
import com.study.myselectshop.dto.ProductResponseDto;
import com.study.myselectshop.security.UserDetailsImpl;
import com.study.myselectshop.service.ProductService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ProductController {

	private final ProductService productService;

	// 관심상품 등록
	@PostMapping("/products")
	public ProductResponseDto createProduct(@RequestBody ProductRequestDto requestDto, @AuthenticationPrincipal
	UserDetailsImpl userDetails) {
		return productService.createProduct(requestDto, userDetails.getUser());
	}

	// 관심상품 희망최저가 업데이트
	@PutMapping("/products/{id}")
	public ProductResponseDto updateProduct(@PathVariable Long id, @RequestBody ProductMyPriceRequestDto requestDto) {
		return productService.updateProduct(id, requestDto);
	}

	@GetMapping("/products")
	public Page<ProductResponseDto> getProducts(
		@RequestParam("page") int page,
		@RequestParam("size") int size,
		@RequestParam("sortBy") String sortBy,
		@RequestParam("isAsc") boolean isAsc,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		return productService.getProducts(userDetails.getUser(), page - 1, size, sortBy, isAsc);
	}

	// 관심상품 폴더에 추가
	@PostMapping("/products/{productId}/folder")
	public void addFolder(@PathVariable Long productId, @RequestParam Long folderId
	, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		productService.addFolder(productId, folderId, userDetails.getUser());
	}
}
