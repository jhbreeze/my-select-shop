package com.study.myselectshop.naver.service;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.study.myselectshop.naver.dto.ItemDto;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "NAVER API")
@Service
public class NaverApiService {

	private final RestTemplate restTemplate;

	public NaverApiService(RestTemplateBuilder restTemplateBuilder) {
		this.restTemplate = restTemplateBuilder.build();
	}

	public List<ItemDto> searchItems(String query) {

		URI uri = UriComponentsBuilder
			.fromUriString("https://openapi.naver.com")
			.path("/v1/serach/shop.json")
			.queryParam("display", 15)
			.queryParam("query", query)
			.encode()
			.build()
			.toUri();
		log.info("==== Request URI : {} ====" , uri);

		RequestEntity<Void> requestEntity = RequestEntity
			.get(uri)
			.header("X-Naver-Client-Id", "QtZEEKrOQkqbk1GGIZ7h")
			.header("X-Naver-Client-Secret", "SsehUnLKI4")
			.build();

		ResponseEntity<String> responseEntity = restTemplate.exchange(requestEntity, String.class);
		log.info("=== NAVER API STATUS CODE : {} ====" , responseEntity.getStatusCode());

		return fromJSONtoItems(responseEntity.getBody());
	}


	public List<ItemDto> fromJSONtoItems(String responseEntity) {
		JSONObject jsonObject = new JSONObject(responseEntity);
		JSONArray items = jsonObject.getJSONArray("items");
		List<ItemDto> itemDtos = new ArrayList<>();

		for(Object item : items) {
			ItemDto itemDto = new ItemDto((JSONObject)item);
			itemDtos.add(itemDto);
		}

		return itemDtos;
	}
}
