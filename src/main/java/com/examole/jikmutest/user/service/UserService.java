package com.examole.jikmutest.user.service;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.examole.jikmutest.global.exception.CustomApiException;
import com.examole.jikmutest.user.dto.request.CreateUserRequestDto;
import com.examole.jikmutest.user.dto.response.CreateUserResponseDto;
import com.examole.jikmutest.user.entity.User;
import com.examole.jikmutest.user.entity.UserRole;
import com.examole.jikmutest.user.exception.UserException;
import com.examole.jikmutest.user.repository.UserRepository;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public CreateUserResponseDto createUser(@Valid CreateUserRequestDto createUserRequestDto) {

		if(userRepository.existsByUsername(createUserRequestDto.getUsername()))
			throw new CustomApiException(UserException.DUPL_USER_ERROR);

		User user = User.builder()
			.id(UUID.randomUUID())
			.username(createUserRequestDto.getUsername())
			.password(createUserRequestDto.getPassword())
			.nickname(createUserRequestDto.getNickname())
			.role(UserRole.USER)
			.build();

		User savedUser = userRepository.save(user);

		return CreateUserResponseDto.fromUser(savedUser);
	}
}
