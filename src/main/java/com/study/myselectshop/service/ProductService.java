package com.study.myselectshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.study.myselectshop.dto.ProductMyPriceRequestDto;
import com.study.myselectshop.dto.ProductRequestDto;
import com.study.myselectshop.dto.ProductResponseDto;
import com.study.myselectshop.entity.Product;
import com.study.myselectshop.entity.User;
import com.study.myselectshop.naver.dto.ItemDto;
import com.study.myselectshop.repository.ProductRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	private final ProductRepository productRepository;

	public static final int MIN_MY_PRICE = 100;

	public ProductResponseDto createProduct(ProductRequestDto requestDto, User user) {
		Product product = productRepository.save(new Product(requestDto, user));
		return new ProductResponseDto(product);
	}

	@Transactional
	public ProductResponseDto updateProduct(Long id, ProductMyPriceRequestDto requestDto) {

		if(requestDto.getMyprice() < MIN_MY_PRICE) {
			throw new IllegalArgumentException("최소가격은 " + MIN_MY_PRICE + "원 이상 설정해야합니다. ");
		}

		Product product = getProductById(id);
		product.update(requestDto);

		return new ProductResponseDto(product);
	}

	private Product getProductById(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new NullPointerException("해당 상품을 찾을 수 없습니다."));
	}

	public List<ProductResponseDto> getProducts(User user) {
		List<Product> products = productRepository.findAllByUser(user);
		List<ProductResponseDto> productResponseDtos = new ArrayList<>();

		for(Product product : products) {
			productResponseDtos.add(new ProductResponseDto(product));
		}

		return productResponseDtos;
	}

	@Transactional
	public void updateBySearch(Long id, ItemDto itemDto) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new NullPointerException("해당 상품을 찾을 수 없습니다. "));

		product.updateByItemDto(itemDto);
	}

	public List<ProductResponseDto> getAllProducts() {
		List<Product> products = productRepository.findAll();
		List<ProductResponseDto> productResponseDtos = new ArrayList<>();

		for(Product product : products) {
			productResponseDtos.add(new ProductResponseDto(product));
		}

		return productResponseDtos;
	}
}
