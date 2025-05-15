package com.examole.jikmutest.user.presentation.controller;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	@Order(1)
	@DisplayName("회원가입 성공 테스트")
	void signupSuccess() throws Exception {

		String createUserJson ="{\n"
			+ "    \"username\":\"testUser1234\",\n"
			+ "    \"password\":\"testUser1234\",\n"
			+ "    \"nickname\":\"테에에에스트유우우저\"\n"
			+ "}";

		mockMvc.perform(post("/signup")
			.contentType(MediaType.APPLICATION_JSON)
			.content(createUserJson))
			.andExpect(status().isCreated())
			.andExpect(jsonPath("$.data").exists())
			.andDo(
				result -> {
					String responseBody = result.getResponse().getContentAsString();

					assertThat(Optional.ofNullable(JsonPath.read(responseBody, "$.data.id"))).isNotNull();
					assertThat(JsonPath.read(responseBody, "$.data.username").toString()).isEqualTo("testUser1234");
					assertThat(JsonPath.read(responseBody, "$.data.nickname").toString()).isEqualTo("테에에에스트유우우저");
					assertThat(JsonPath.read(responseBody, "$.data.role").toString()).isEqualTo("USER");
				}
			);
	}

	@Test
	@Order(2)
	@DisplayName("회원가입 중복 실패 테스트")
	void signupDuplFail() throws Exception {

		String createUserJson ="{\n"
			+ "    \"username\":\"testUser1234\",\n"
			+ "    \"password\":\"testUser1234\",\n"
			+ "    \"nickname\":\"테에에에스트유우우저\"\n"
			+ "}";

		// 중복에러 확인
		mockMvc.perform(post("/signup")
				.contentType(MediaType.APPLICATION_JSON)
				.content(createUserJson))
			.andExpect(status().isConflict())
			.andExpect(jsonPath("$.code").value("E_DUPL_USERNAME"))
			.andExpect(jsonPath("$.message").value("중복된 아이디가 존재합니다."))
			.andExpect(jsonPath("$.status").value(409));
	}

	@Test
	@Order(3)
	@DisplayName("로그인 성공 테스트")
	void loginSuccess() throws Exception {

		String loginJson = "{\n"
			+ "    \"username\":\"testUser1234\",\n"
			+ "    \"password\":\"testUser1234\"\n"
			+ "}";

		mockMvc.perform(post("/login")
			.contentType(MediaType.APPLICATION_JSON)
			.content(loginJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("S_LOGIN"))
			.andExpect(jsonPath("$.message").value("로그인에 성공했습니다."))
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.data").exists())
			.andDo(
				result -> {
					String responseBody = result.getResponse().getContentAsString();

					assertThat(Optional.ofNullable(JsonPath.read(responseBody, "$.data.token"))).isNotNull();
					String token = JsonPath.read(responseBody, "$.data.token");
					System.out.println("발급된 JWT 토큰: " + token);
				}
			);
	}

	@Test
	@Order(4)
	@DisplayName("로그인 실패 테스트")
	void loginFail() throws Exception {

		String loginJson = "{\n"
			+ "    \"username\":\"testUser1234\",\n"
			+ "    \"password\":\"testUser12341234\"\n"
			+ "}";

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson))
			.andExpect(status().isBadRequest())
			.andExpect(jsonPath("$.code").value("E_INVALID"))
			.andExpect(jsonPath("$.message").value("아이디 또는 비밀번호가 올바르지 않습니다."))
			.andExpect(jsonPath("$.status").value(400));
	}

	@Test
	@Order(5)
	@DisplayName("일반 사용자가 요청할떄 권한 오류 테스트")
	void normalUserAccessDenied() throws Exception {

		String loginJson = "{\n"
			+ "    \"username\":\"testUser1234\",\n"
			+ "    \"password\":\"testUser1234\"\n"
			+ "}";

		AtomicReference<String> token = new AtomicReference<>();

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("S_LOGIN"))
			.andExpect(jsonPath("$.message").value("로그인에 성공했습니다."))
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.data").exists())
			.andDo(
				result -> {
					String responseBody = result.getResponse().getContentAsString();

					assertThat(Optional.ofNullable(JsonPath.read(responseBody, "$.data.token"))).isNotNull();
					token.set(JsonPath.read(responseBody, "$.data.token"));
					System.out.println("발급된 JWT 토큰: " + token);
				}
			);

		// 권한없는 접근
		String pathVariable = "testUser1234";
		mockMvc.perform(patch("/admin/users/" + pathVariable + "/roles")
				.header("Authorization", "Bearer " + token.get()))
			.andExpect(status().isForbidden());
	}


	@Test
	@Order(6)
	@DisplayName("존재하지 않는 사용자를 ADMIN 권한 부여할때 실패 테스트")
	void notExistUserFailTest() throws Exception {

		// 이번 테스트에서 스프링 부트가 뜰 때 자동으로 삽입되는 어드민 계정을 제외한 어느 유저도 회원가입 하지않고 진행하여 테스트함.
		String loginJson = "{\n"
			+ "    \"username\":\"TestAdmin1234\",\n"
			+ "    \"password\":\"TestAdmin1234\"\n"
			+ "}";

		AtomicReference<String> token = new AtomicReference<>();

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("S_LOGIN"))
			.andExpect(jsonPath("$.message").value("로그인에 성공했습니다."))
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.data").exists())
			.andDo(
				result -> {
					String responseBody = result.getResponse().getContentAsString();

					assertThat(Optional.ofNullable(JsonPath.read(responseBody, "$.data.token"))).isNotNull();
					token.set(JsonPath.read(responseBody, "$.data.token"));
					System.out.println("발급된 JWT 토큰: " + token);
				}
			);

		// 없는 유저
		String pathVariable = "notExistUser1234";
		mockMvc.perform(patch("/admin/users/" + pathVariable + "/roles")
				.header("Authorization", "Bearer " + token.get()))
			.andExpect(status().isNotFound())
			.andExpect(jsonPath("$.code").value("E_NOT_FOUND_USER"))
			.andExpect(jsonPath("$.message").value("해당 유저를 찾을 수 없습니다."))
			.andExpect(jsonPath("$.status").value(404));
	}

	@Test
	@Order(7)
	@DisplayName("ADMIN 권한 부여 성공 테스트")
	void assignAdminRole() throws Exception {

		// 테스트용 어드민 계정은 부트 시작시에만 생성
		String loginJson = "{\n"
			+ "    \"username\":\"TestAdmin1234\",\n"
			+ "    \"password\":\"TestAdmin1234\"\n"
			+ "}";

		AtomicReference<String> token = new AtomicReference<>();

		mockMvc.perform(post("/login")
				.contentType(MediaType.APPLICATION_JSON)
				.content(loginJson))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("S_LOGIN"))
			.andExpect(jsonPath("$.message").value("로그인에 성공했습니다."))
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.data").exists())
			.andDo(
				result -> {
					String responseBody = result.getResponse().getContentAsString();

					assertThat(Optional.ofNullable(JsonPath.read(responseBody, "$.data.token"))).isNotNull();
					token.set(JsonPath.read(responseBody, "$.data.token"));
					System.out.println("발급된 JWT 토큰: " + token);
				}
			);
		
		// 일반유저 -> 어드민 권한 주기
		String pathVariable = "testUser1234";
		mockMvc.perform(patch("/admin/users/" + pathVariable + "/roles")
				.header("Authorization", "Bearer " + token.get()))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.code").value("S_CHANGE_ROLE"))
			.andExpect(jsonPath("$.message").value("어드민 권한변경이 성공했습니다."))
			.andExpect(jsonPath("$.status").value(200))
			.andExpect(jsonPath("$.data").exists())
			.andDo(
				result -> {
					String responseBody = result.getResponse().getContentAsString();

					assertThat(Optional.ofNullable(JsonPath.read(responseBody, "$.data.id"))).isNotNull();
					assertThat(JsonPath.read(responseBody, "$.data.username").toString()).isEqualTo("testUser1234");
					assertThat(JsonPath.read(responseBody, "$.data.nickname").toString()).isEqualTo("테에에에스트유우우저");
					
					// USER -> ADMIN 변화
					assertThat(JsonPath.read(responseBody, "$.data.role").toString()).isEqualTo("ADMIN");
				}
			);
	}

}