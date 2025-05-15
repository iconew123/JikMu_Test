package com.examole.jikmutest.user.application.service;

import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.examole.jikmutest.global.exception.CustomApiException;
import com.examole.jikmutest.user.application.dto.request.CreateUserRequestDto;
import com.examole.jikmutest.user.application.dto.request.LoginRequestDto;
import com.examole.jikmutest.user.application.dto.response.ChangeRoleResponseDto;
import com.examole.jikmutest.user.application.dto.response.CreateUserResponseDto;
import com.examole.jikmutest.user.application.dto.response.LoginResponseDto;
import com.examole.jikmutest.user.application.exception.UserException;
import com.examole.jikmutest.user.domain.model.User;
import com.examole.jikmutest.user.domain.model.UserRole;
import com.examole.jikmutest.user.domain.repository.UserRepository;
import com.examole.jikmutest.user.infrastructure.jwt.JwtUtil;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

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
			throw new CustomApiException(UserException.INVALID);
		}

		UUID userUuid = targetUser.getId();
		String userName = targetUser.getUsername();
		UserRole userRole = targetUser.getRole();

		String accessToken = jwtUtil.createAccessToken(userUuid, userName, userRole);

		return LoginResponseDto.builder()
			.token(accessToken)
			.build();
	}

	public ChangeRoleResponseDto assignAdminRole(String username) {

		User targetUser = userRepository.findByUsername(username);

		if (targetUser == null) {
			throw new CustomApiException(UserException.NOT_FOUND_USER);
		}

		targetUser.update(UserRole.ADMIN);

		return ChangeRoleResponseDto.fromUser(targetUser);
	}
}
