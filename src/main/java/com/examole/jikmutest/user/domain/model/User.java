package com.examole.jikmutest.user.domain.model;

import java.util.UUID;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

	private UUID id;
	private String username;
	private String password;
	private String nickname;
	private UserRole role;

	public void update(UserRole userRole) {
		this.role = userRole;
	}
}
