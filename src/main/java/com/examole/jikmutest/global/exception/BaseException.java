package com.examole.jikmutest.global.exception;

import org.springframework.http.HttpStatus;

import com.examole.jikmutest.global.code.ServiceCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum BaseException implements ServiceCode {
	INVALID_INPUT(HttpStatus.BAD_REQUEST, "유효성 검증에 실패하였습니다.", "E_VALID_INPUT"),
	SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버에 오류가 발생하였습니다.", "E_SERVER_ERROR" ),;

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
