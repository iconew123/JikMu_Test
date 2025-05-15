package com.examole.jikmutest.user.application.service;

import com.examole.jikmutest.user.application.dto.request.CreateUserRequestDto;
import com.examole.jikmutest.user.application.dto.request.LoginRequestDto;
import com.examole.jikmutest.user.application.dto.response.ChangeRoleResponseDto;
import com.examole.jikmutest.user.application.dto.response.CreateUserResponseDto;
import com.examole.jikmutest.user.application.dto.response.LoginResponseDto;

import jakarta.validation.Valid;

public interface UserService {
	CreateUserResponseDto createUser(@Valid CreateUserRequestDto createUserRequestDto);

	LoginResponseDto login(@Valid LoginRequestDto loginRequestDto);

	ChangeRoleResponseDto assignAdminRole(String userId);
}
