package com.examole.jikmutest.user.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateUserRequestDto {

	@NotBlank(message = "유저 이름은 공백일 수 없습니다.")
	@Size(min = 4, max = 20, message = "유저 이름은 4자 이상 20자 이하여야 합니다.")
	private String username;

	@NotBlank(message = "비밀번호는 공백일 수 없습니다.")
	@Size(min = 8, max = 20)
	private String password;

	@NotBlank(message = "유저 닉네임은 공백일 수 없습니다.")
	@Size(min = 4, max = 20, message = "유저 닉네임은 4자 이상 20자 이하여야 합니다.")
	private String nickname;

}
