package com.examole.jikmutest.global.dto;

import org.springframework.http.HttpStatus;

import com.examole.jikmutest.global.code.ServiceCode;
import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CommonResponse<T> {

	private String code;
	private String message;
	private int status;

	@JsonInclude(JsonInclude.Include.NON_NULL)
	private T data;

	public CommonResponse(ServiceCode serviceCode, T data) {
		this.code = serviceCode.getCode();
		this.message = serviceCode.getMessage();
		this.status = serviceCode.getStatus().value();
		this.data = data;
	}

	public CommonResponse(ServiceCode serviceCode) {
		this.code = serviceCode.getCode();
		this.message = serviceCode.getMessage();
		this.status = serviceCode.getStatus().value();
	}
}
