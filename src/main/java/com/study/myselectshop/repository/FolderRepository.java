package com.study.myselectshop.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.myselectshop.entity.Folder;
import com.study.myselectshop.entity.User;

public interface FolderRepository extends JpaRepository<Folder, Long> {

	List<Folder> findAllByUserAndNameIn(User user, List<String> folderNames);

	List<Folder> findAllByUser(User user);
}
