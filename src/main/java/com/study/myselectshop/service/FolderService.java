package com.study.myselectshop.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.study.myselectshop.dto.FolderResponseDto;
import com.study.myselectshop.entity.Folder;
import com.study.myselectshop.entity.User;
import com.study.myselectshop.repository.FolderRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class FolderService {

	private final FolderRepository folderRepository;

	public void addFolders(List<String> reqFolderNames, User user) {
		List<Folder> existFolders = folderRepository.findAllByUserAndNameIn(user, reqFolderNames);
		List<Folder> folders = new ArrayList<>();

		for(String reqFolderName : reqFolderNames) {
			if(!isExistFolderName(reqFolderName, existFolders)) {
				Folder folder = new Folder(reqFolderName, user);
				folders.add(folder);
			} else {
				throw new IllegalArgumentException("중복된 폴더명입니다. ");
			}
		}

		folderRepository.saveAll(folders);
	}

	private boolean isExistFolderName(String reqFolderName, List<Folder> existFolders) {
		for(Folder existFolder : existFolders) {
			if (reqFolderName.equals(existFolder.getName())) {
				return true;
			}
		}

		return false;
	}

	public List<FolderResponseDto> getFolders(User user) {
		List<Folder> folders = folderRepository.findAllByUser(user);
		List<FolderResponseDto> folderResponseDtos = new ArrayList<>();

		for (Folder folder : folders) {
			folderResponseDtos.add(new FolderResponseDto(folder));
		}

		return folderResponseDtos;
	}

}
