package com.examole.jikmutest.user.success;

import org.springframework.http.HttpStatus;

import com.examole.jikmutest.global.code.ServiceCode;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum SuccessCode implements ServiceCode {

	USER_SIGNUP(HttpStatus.CREATED, "회원가입에 성공했습니다.", "S_SIGNUP"),
	USER_LOGIN(HttpStatus.OK, "로그인에 성공했습니다.", "S_LOGIN" ),
	USER_CHANGE_ROLE(HttpStatus.OK, "어드민 권한변경이 성공했습니다.", "S_CHANGE_ROLE" ),;

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
