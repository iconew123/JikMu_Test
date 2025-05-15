package com.examole.jikmutest.user.infrastructure.config;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.examole.jikmutest.user.domain.model.User;
import com.examole.jikmutest.user.domain.model.UserRole;
import com.examole.jikmutest.user.infrastructure.persistence.UserRepositoryImpl;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class UserDataInitializer {

	private final UserRepositoryImpl userRepositoryImpl;
	private final PasswordEncoder passwordEncoder;

	@PostConstruct
	public void init() {
		
		if (!userRepositoryImpl.existsByUsername("TestAdmin1234")) {
			User admin = User.builder()
				.id(UUID.randomUUID())
				.username("TestAdmin1234")
				.password(passwordEncoder.encode("TestAdmin1234"))
				.nickname("관리자")
				.role(UserRole.ADMIN)
				.build();

			userRepositoryImpl.save(admin);
			
			log.info("ADMIN 계정 생성 완료");
		}
	}
}