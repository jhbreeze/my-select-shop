package com.study.myselectshop.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.study.myselectshop.dto.FolderRequestDto;
import com.study.myselectshop.dto.FolderResponseDto;
import com.study.myselectshop.dto.ProductResponseDto;
import com.study.myselectshop.security.UserDetailsImpl;
import com.study.myselectshop.service.FolderService;
import com.study.myselectshop.service.ProductService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FolderController {

	private final FolderService folderService;
	private final ProductService productService;

	@PostMapping("/folders")
	public void addFolders(@RequestBody FolderRequestDto folderRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		folderService.addFolders(folderRequestDto.getFolderNames(), userDetails.getUser());
	}

	@GetMapping("/folders")
	public List<FolderResponseDto> getFolders(@AuthenticationPrincipal UserDetailsImpl userDetails) {
		return folderService.getFolders(userDetails.getUser());
	}

	@GetMapping("/folders/{folderId}/products")
	public Page<ProductResponseDto> getProductsInFolder(@PathVariable Long folderId,
		@RequestParam int page,
		@RequestParam int size,
		@RequestParam String sortBy,
		@RequestParam boolean isAsc,
		@AuthenticationPrincipal UserDetailsImpl userDetails
	) {
		return productService.getProductsInFolder(
			folderId,
			page - 1,
			size,
			sortBy,
			isAsc,
			userDetails.getUser()
		);

	}
}
