package com.examole.jikmutest.user.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class LoginRequestDto {

	@NotBlank(message = "유저이름은 공백일 수 없습니다.")
	private String username;

	@NotBlank(message = "비밀번호는 공백일 수 없습니다.")
	private String password;
}
