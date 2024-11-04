package com.study.myselectshop.service;

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.study.myselectshop.dto.SignupRequestDto;
import com.study.myselectshop.entity.User;
import com.study.myselectshop.entity.UserRoleEnum;
import com.study.myselectshop.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	// ADMIN_TOKEN
	private final String ADMIN_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

	public void signup(SignupRequestDto requestDto) {
		String username = requestDto.getUsername();
		String password = passwordEncoder.encode(requestDto.getPassword());

		log.info("암호화된 비밀번호: {}", password);

		// 회원 중복 확인
		Optional<User> checkUsername = userRepository.findByUsername(username);
		if (checkUsername.isPresent()) {
			throw new IllegalArgumentException("중복된 사용자가 존재합니다.");
		}

		// email 중복확인
		String email = requestDto.getEmail();
		Optional<User> checkEmail = userRepository.findByEmail(email);
		if (checkEmail.isPresent()) {
			throw new IllegalArgumentException("중복된 Email 입니다.");
		}

		// 사용자 ROLE 확인
		UserRoleEnum role = UserRoleEnum.USER;
		if (requestDto.isAdmin()) {
			if (!ADMIN_TOKEN.equals(requestDto.getAdminToken())) {
				throw new IllegalArgumentException("관리자 암호가 틀려 등록이 불가능합니다.");
			}
			role = UserRoleEnum.ADMIN;
		}

		// 사용자 등록
		User user = new User(username, password, email, role);
		userRepository.save(user);
	}
}
