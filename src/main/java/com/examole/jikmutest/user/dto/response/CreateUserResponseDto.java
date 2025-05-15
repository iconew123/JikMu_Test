package com.examole.jikmutest.user.dto.response;

import java.util.UUID;

import com.examole.jikmutest.user.entity.User;
import com.examole.jikmutest.user.entity.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateUserResponseDto {

	private UUID id;
	private String username;
	private String password;
	private String nickname;
	private UserRole role;

	public static CreateUserResponseDto fromUser(User savedUser) {

		return CreateUserResponseDto.builder()
			.id(savedUser.getId())
			.username(savedUser.getUsername())
			.password(savedUser.getPassword())
			.nickname(savedUser.getNickname())
			.role(savedUser.getRole())
			.build();
	}
}
