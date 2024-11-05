package com.study.myselectshop.dto;

import java.util.ArrayList;
import java.util.List;

import com.study.myselectshop.entity.Product;
import com.study.myselectshop.entity.ProductFolderRelation;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductResponseDto {
	private Long id;
	private String title;
	private String link;
	private String image;
	private int lprice;
	private int myprice;

	private List<FolderResponseDto> productFolderList = new ArrayList<>();

	public ProductResponseDto(Product product) {
		this.id = product.getId();
		this.title = product.getTitle();
		this.link = product.getLink();
		this.image = product.getImage();
		this.lprice = product.getLprice();
		this.myprice = product.getMyprice();

		for(ProductFolderRelation relation : product.getProductFolders()) {
			productFolderList.add(new FolderResponseDto(relation.getFolder())); // 지연로딩 필요 -> 트랜잭션 환경에서 동작 -> 어노테이션 필요 -> 조회 api 이므로 성능 고려하여 readOnly 옵션 설정
		}
	}
}