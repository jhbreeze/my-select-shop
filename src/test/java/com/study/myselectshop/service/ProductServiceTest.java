package com.study.myselectshop.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.study.myselectshop.dto.ProductMyPriceRequestDto;
import com.study.myselectshop.entity.Product;
import com.study.myselectshop.entity.User;
import com.study.myselectshop.entity.UserRoleEnum;
import com.study.myselectshop.repository.ProductRepository;
import com.study.myselectshop.repository.UserRepository;

@SpringBootTest
class ProductServiceTest {

	@Autowired
	private ProductService productService;

	@Autowired
	private ProductRepository productRepository;

	@Autowired
	private UserRepository userRepository;

	@Test
	void 유효한_희망가격() {
		// given
		User user = new User();
		user.setUsername("testUser");
		user.setPassword("password");
		user.setEmail("test@test.com");
		user.setRole(UserRoleEnum.USER);

		userRepository.save(user);

		Product product = new Product();
		product.setTitle("test");
		product.setLprice(500000);
		product.setLink("https://search.shopping.naver.com/catalog/27058613526");
		product.setImage("https://shopping-phinf.pstatic.net/main_2705861/27058613526.20220415092817.jpg");
		product.setMyprice(1000);  // 초기가격
		product.setUser(user);
		Product savedProduct = productRepository.save(product);

		ProductMyPriceRequestDto requestDto = new ProductMyPriceRequestDto();
		requestDto.setMyprice(50);  // 최소가격보다 작은 값

		// when
		assertThrows(IllegalArgumentException.class, () -> {
			productService.updateProduct(savedProduct.getId(), requestDto);
		});

		// then
		Product foundProduct = productRepository.findById(savedProduct.getId()).orElseThrow();
		assertEquals(1000, foundProduct.getMyprice());  // 원래 가격이 유지되어야 함
	}
}