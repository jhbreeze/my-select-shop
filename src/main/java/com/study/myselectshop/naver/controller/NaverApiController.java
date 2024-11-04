package com.study.myselectshop.naver.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.myselectshop.naver.dto.ItemDto;
import com.study.myselectshop.naver.service.NaverApiService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class NaverApiController {

	private final NaverApiService naverApiService;

	@GetMapping("/search")
	public List<ItemDto> searchItems(@RequestParam String query) {
		return naverApiService.searchItems(query);
	}

}
