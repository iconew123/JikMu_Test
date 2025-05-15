package com.examole.jikmutest.user.config;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.examole.jikmutest.user.entity.User;
import com.examole.jikmutest.user.entity.UserRole;
import com.examole.jikmutest.user.repository.UserRepository;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataInitializer {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {
		
		if (!userRepository.existsByUsername("TestAdmin1234")) {
			User admin = User.builder()
				.id(UUID.randomUUID())
				.username("TestAdmin1234")
				.password(passwordEncoder.encode("TestAdmin1234"))
				.nickname("관리자")
				.role(UserRole.ADMIN)
				.build();

			userRepository.save(admin);
			
			log.info("ADMIN 계정 생성 완료");
		}
	}
}