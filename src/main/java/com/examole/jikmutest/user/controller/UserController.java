package com.examole.jikmutest.user.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.examole.jikmutest.global.dto.CommonResponse;
import com.examole.jikmutest.user.dto.request.CreateUserRequestDto;
import com.examole.jikmutest.user.dto.response.CreateUserResponseDto;
import com.examole.jikmutest.user.service.UserService;
import com.examole.jikmutest.user.success.SuccessCode;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class UserController {

	private final UserService userService;

	@PostMapping("/signup")
	public ResponseEntity<CommonResponse<CreateUserResponseDto>> signup(
		@RequestBody @Valid CreateUserRequestDto createUserRequestDto
	){

		CreateUserResponseDto createUserResponseDto = userService.createUser(createUserRequestDto);

		URI location = ServletUriComponentsBuilder
			.fromCurrentContextPath()
			.path("/signup")
			.build()
			.toUri();

		return ResponseEntity
			.created(location)
			.body(new CommonResponse<>(SuccessCode.USER_SIGNUP, createUserResponseDto));
	}

}
