package com.examole.jikmutest.global.code;

import org.springframework.http.HttpStatus;

public interface ServiceCode {

	HttpStatus getStatus();

	String getMessage();

	String getCode();
}
