package com.study.myselectshop.entity;

import static jakarta.persistence.FetchType.*;

import com.study.myselectshop.dto.ProductMyPriceRequestDto;
import com.study.myselectshop.dto.ProductRequestDto;
import com.study.myselectshop.naver.dto.ItemDto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
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

	@ManyToOne(fetch = LAZY)
	@JoinColumn(name = "user_id", nullable = false)
	public User user;

	public Product(ProductRequestDto requestDto, User user) {
		this.title = requestDto.getTitle();
		this.image = requestDto.getImage();
		this.link = requestDto.getLink();
		this.lprice = requestDto.getLprice();
		this.user = user;
	}

	public void update(ProductMyPriceRequestDto requestDto) {
		this.myprice = requestDto.getMyprice();
	}

	public void updateByItemDto(ItemDto itemDto) {
		this.lprice = itemDto.getLprice();
	}
}
