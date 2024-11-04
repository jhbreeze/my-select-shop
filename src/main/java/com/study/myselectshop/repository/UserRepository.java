package com.study.myselectshop.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.study.myselectshop.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	Optional<User> findByUsername(String username);
	Optional<User> findByEmail(String email);
}
