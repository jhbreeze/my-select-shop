package com.study.myselectshop.entity;


import com.study.myselectshop.dto.ProductMyPriceRequestDto;
import com.study.myselectshop.dto.ProductRequestDto;
import com.study.myselectshop.naver.dto.ItemDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "product")
@NoArgsConstructor
public class Product extends Timestamped {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String image;

	@Column(nullable = false)
	private String link;

	@Column(nullable = false)
	private int lprice;

	@Column(nullable = false)
	private int myprice;

	public Product(ProductRequestDto requestDto) {
		this.title = requestDto.getTitle();
		this.image = requestDto.getImage();
		this.link = requestDto.getLink();
		this.lprice = requestDto.getLprice();
	}

	public void update(ProductMyPriceRequestDto requestDto) {
		this.myprice = requestDto.getMyprice();
	}

	public void updateByItemDto(ItemDto itemDto) {
		this.lprice = itemDto.getLprice();
	}
}
