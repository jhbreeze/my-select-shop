package com.study.myselectshop.naver.dto;

import org.json.JSONObject;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ItemDto {
	private String title;
	private String link;
	private String image;
	private int lprice;

	public ItemDto(JSONObject itemJson) {
		// JSONObject로부터 데이터를 추출하여 DTO 필드에 매핑
		this.title = itemJson.getString("title");
		this.link = itemJson.getString("link");
		this.image = itemJson.getString("image");
		this.lprice = itemJson.getInt("lprice");
	}
}
