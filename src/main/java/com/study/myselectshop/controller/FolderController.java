package com.study.myselectshop.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.study.myselectshop.dto.FolderRequestDto;
import com.study.myselectshop.security.UserDetailsImpl;
import com.study.myselectshop.service.FolderService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class FolderController {

	private final FolderService folderService;

	@PostMapping("/folders")
	public void addFolders(@RequestBody FolderRequestDto folderRequestDto,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		folderService.addFolders(folderRequestDto.getFolderNames(), userDetails.getUser());
	}

}
