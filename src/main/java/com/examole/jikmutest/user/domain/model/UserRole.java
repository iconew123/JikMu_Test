package com.examole.jikmutest.user.domain.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum UserRole {
	USER(Authority.USER),
	ADMIN(Authority.ADMIN);

	private final String value;

	public static class Authority{

		public static final String USER = "ROLE_USER";
		public static final String ADMIN = "ROLE_ADMIN";
	}
}
