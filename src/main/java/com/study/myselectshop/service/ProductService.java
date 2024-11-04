package com.study.myselectshop.service;

import org.springframework.stereotype.Service;

import com.study.myselectshop.dto.ProductMyPriceRequestDto.ProductMyPriceRequestDto;
import com.study.myselectshop.dto.ProductRequestDto.ProductRequestDto;
import com.study.myselectshop.dto.ProductResponseDto.ProductResponseDto;
import com.study.myselectshop.entity.Product;
import com.study.myselectshop.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public static final int MIN_MY_PRICE = 100;

	public ProductResponseDto createProduct(ProductRequestDto requestDto) {
		Product product = productRepository.save(new Product(requestDto));
		return new ProductResponseDto(product);
	}

	@Transactional
	public ProductResponseDto updateProduct(Long id, ProductMyPriceRequestDto requestDto) {

		validRequest(requestDto.getMyprice());

		Product product = getProductById(id);
		product.update(requestDto);

		return new ProductResponseDto(product);
	}

	private Product getProductById(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new NullPointerException("해당 상품을 찾을 수 없습니다."));
	}

	private static void validRequest(int myprice) {
		if(myprice < MIN_MY_PRICE) {
			throw new IllegalArgumentException("최소가격은 " + MIN_MY_PRICE + "원 이상 설정해야합니다. ");
		}
	}
}
