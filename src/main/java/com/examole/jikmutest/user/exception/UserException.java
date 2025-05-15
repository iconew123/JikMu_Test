package com.examole.jikmutest.user.exception;

import org.springframework.http.HttpStatus;

import com.examole.jikmutest.global.code.ServiceCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum UserException implements ServiceCode {

	DUPL_USER_ERROR(HttpStatus.CONFLICT, "중복된 아이디가 존재합니다.", "E_DUPL_USERNAME");

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
