package com.study.myselectshop.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.study.myselectshop.dto.ProductMyPriceRequestDto;
import com.study.myselectshop.dto.ProductRequestDto;
import com.study.myselectshop.dto.ProductResponseDto;
import com.study.myselectshop.entity.Folder;
import com.study.myselectshop.entity.Product;
import com.study.myselectshop.entity.ProductFolderRelation;
import com.study.myselectshop.entity.User;
import com.study.myselectshop.entity.UserRoleEnum;
import com.study.myselectshop.naver.dto.ItemDto;
import com.study.myselectshop.repository.FolderRepository;
import com.study.myselectshop.repository.ProductFolderRelationRepository;
import com.study.myselectshop.repository.ProductRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ProductService {

	public static final int MIN_MY_PRICE = 100;

	private final ProductRepository productRepository;
	private final FolderRepository folderRepository;
	private final ProductFolderRelationRepository productFolderRelationRepository;

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

	@Transactional(readOnly = true)
	public Page<ProductResponseDto> getProducts(User user,
		int page, int size, String sortBy, boolean isAsc) {

		// Paging
		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		UserRoleEnum userRole = user.getRole();

		Page<Product> products;

		if(userRole == UserRoleEnum.USER) {
			products = productRepository.findAllByUser(user, pageable);
		} else {
			products = productRepository.findAll(pageable);
		}

		return products.map(ProductResponseDto::new);
	}

	@Transactional
	public void updateBySearch(Long id, ItemDto itemDto) {
		Product product = productRepository.findById(id)
			.orElseThrow(() -> new NullPointerException("해당 상품을 찾을 수 없습니다. "));

		product.updateByItemDto(itemDto);
	}

	public void addFolder(Long productId, Long folderId, User user) {

		Product product = productRepository.findById(productId)
			.orElseThrow(() -> new NullPointerException("not found product"));

		Folder folder = folderRepository.findById(folderId)
			.orElseThrow(() -> new NullPointerException("not found folder"));

		if( ! product.getUser().getId().equals(user.getId())
			|| ! folder.getUser().getId().equals(user.getId()) ) {

			throw new IllegalArgumentException("회원님의 관심상품이 아니거나, 회원님의 폴더가 아닙니다.");
		}

		Optional<ProductFolderRelation> overlapFolder = productFolderRelationRepository.findByProductAndFolder(product, folder);
		if(overlapFolder.isPresent()) {
			throw new IllegalArgumentException("중복된 폴더입니다. ");
		}

		productFolderRelationRepository.save(new ProductFolderRelation(product, folder));
	}

	public Page<ProductResponseDto> getProductsInFolder(Long folderId, int page, int size, String sortBy, boolean isAsc,
		User user) {

		Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
		Sort sort = Sort.by(direction, sortBy);
		Pageable pageable = PageRequest.of(page, size, sort);

		Page<Product> products = productRepository.findAllByUserAndProductFolders_FolderId(
			user, folderId, pageable);

		return products.map(ProductResponseDto::new);
	}
}
