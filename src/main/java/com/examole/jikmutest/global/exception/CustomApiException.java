package com.examole.jikmutest.global.exception;

import org.springframework.http.HttpStatus;

import com.examole.jikmutest.global.code.ServiceCode;

import lombok.Getter;

@Getter
public class CustomApiException extends RuntimeException {

	private final HttpStatus status;
	private final ServiceCode serviceCode;

	public CustomApiException(ServiceCode serviceCode){
		super(serviceCode.getMessage());
		this.serviceCode = serviceCode;
		this.status = serviceCode.getStatus();
	}
}