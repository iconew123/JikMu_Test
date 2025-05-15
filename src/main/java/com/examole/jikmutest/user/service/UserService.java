package com.examole.jikmutest.user.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examole.jikmutest.global.exception.CustomApiException;
import com.examole.jikmutest.user.dto.request.CreateUserRequestDto;
import com.examole.jikmutest.user.dto.request.LoginRequestDto;
import com.examole.jikmutest.user.dto.response.CreateUserResponseDto;
import com.examole.jikmutest.user.dto.response.LoginResponseDto;
import com.examole.jikmutest.user.entity.User;
import com.examole.jikmutest.user.entity.UserRole;
import com.examole.jikmutest.user.exception.UserException;
import com.examole.jikmutest.user.jwt.JwtUtil;
import com.examole.jikmutest.user.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public CreateUserResponseDto createUser(@Valid CreateUserRequestDto createUserRequestDto) {

		if(userRepository.existsByUsername(createUserRequestDto.getUsername()))
			throw new CustomApiException(UserException.DUPL_USER_ERROR);

		String encodedPassword = passwordEncoder.encode(createUserRequestDto.getPassword());

		User user = User.builder()
			.id(UUID.randomUUID())
			.username(createUserRequestDto.getUsername())
			.password(encodedPassword)
			.nickname(createUserRequestDto.getNickname())
			.role(UserRole.USER)
			.build();

		User savedUser = userRepository.save(user);

		return CreateUserResponseDto.fromUser(savedUser);
	}

	public LoginResponseDto login(@Valid LoginRequestDto loginRequestDto) {

		User targetUser = userRepository.findByUsername(loginRequestDto.getUsername());

		if (targetUser == null) {
			throw new CustomApiException(UserException.NOT_FOUND_USER);
		}

		if (!passwordEncoder.matches(loginRequestDto.getPassword(), targetUser.getPassword())) {
			throw new CustomApiException(UserException.INVALID_PASSWORD);
		}

		UUID userUuid = targetUser.getId();
		String userName = targetUser.getUsername();
		UserRole userRole = targetUser.getRole();

		String accessToken = jwtUtil.createAccessToken(userUuid, userName, userRole);

		return LoginResponseDto.builder()
			.token(accessToken)
			.build();
	}
}
