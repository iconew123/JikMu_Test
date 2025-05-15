package com.examole.jikmutest.user.exception;

import org.springframework.http.HttpStatus;

import com.examole.jikmutest.global.code.ServiceCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserException implements ServiceCode {

	INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "잘못된 JWT 서명입니다.", "E_INVALID_JWT_SIGNATURE"),
	EXPIRED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "만료된 JWT 토큰입니다.", "E_EXPIRED_JWT_TOKEN"),
	UNSUPPORTED_JWT_TOKEN(HttpStatus.UNAUTHORIZED, "지원되지 않는 JWT 토큰입니다.", "E_UNSUPPORTED_JWT_TOKEN"),
	JWT_CLAIM_IS_EMPTY(HttpStatus.UNAUTHORIZED, "JWT 토큰이 비어있습니다.", "E_JWT_CLAIM_IS_EMPTY"),
	DUPL_USER_ERROR(HttpStatus.CONFLICT, "중복된 아이디가 존재합니다.", "E_DUPL_USERNAME"),
	NOT_FOUND_USER(HttpStatus.NOT_FOUND, "해당 유저를 찾을 수 없습니다.", "E_NOT_FOUND_USER" ),
	INVALID_PASSWORD(HttpStatus.BAD_REQUEST, "비밀번호가 틀렸습니다.", "E_INVALID_PASSWORD" ),;

	private final HttpStatus status;
	private final String message;
	private final String code;

	@Override
	public HttpStatus getStatus() {
		return this.status;
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public String getCode() {
		return this.code;
	}
}
