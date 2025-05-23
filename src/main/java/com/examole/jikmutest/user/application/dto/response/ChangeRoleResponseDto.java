package com.examole.jikmutest.user.application.dto.response;

import java.util.UUID;

import com.examole.jikmutest.user.domain.model.User;
import com.examole.jikmutest.user.domain.model.UserRole;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class ChangeRoleResponseDto {

	private UUID id;
	private String username;
	private String nickname;
	private UserRole role;

	public static ChangeRoleResponseDto fromUser(User targetUser) {

		return ChangeRoleResponseDto.builder()
			.id(targetUser.getId())
			.username(targetUser.getUsername())
			.nickname(targetUser.getNickname())
			.role(targetUser.getRole())
			.build();
	}
}
