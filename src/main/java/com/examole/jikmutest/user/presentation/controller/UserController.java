package com.examole.jikmutest.user.presentation.controller;

import java.net.URI;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.examole.jikmutest.global.dto.CommonResponse;
import com.examole.jikmutest.user.application.dto.request.CreateUserRequestDto;
import com.examole.jikmutest.user.application.dto.request.LoginRequestDto;
import com.examole.jikmutest.user.application.dto.response.ChangeRoleResponseDto;
import com.examole.jikmutest.user.application.dto.response.CreateUserResponseDto;
import com.examole.jikmutest.user.application.dto.response.LoginResponseDto;
import com.examole.jikmutest.user.application.service.UserService;
import com.examole.jikmutest.user.application.success.SuccessCode;

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

	@PostMapping("/login")
	public ResponseEntity<CommonResponse<LoginResponseDto>> login(
		@RequestBody @Valid LoginRequestDto loginRequestDto
	){

		LoginResponseDto loginResponseDto = userService.login(loginRequestDto);

		return ResponseEntity
			.ok()
			.body(new CommonResponse<>(SuccessCode.USER_LOGIN, loginResponseDto));
	}

	@PatchMapping("/admin/users/{userId}/roles")
	@PreAuthorize("hasAnyRole('ADMIN')")
	public ResponseEntity<CommonResponse<ChangeRoleResponseDto>> changeRole(
		@PathVariable String userId
	){

		ChangeRoleResponseDto changeRoleResponseDto = userService.assignAdminRole(userId);

		return ResponseEntity
			.ok()
			.body(new CommonResponse<>(SuccessCode.USER_CHANGE_ROLE, changeRoleResponseDto));
	}
}
