package com.examole.jikmutest.user.filter;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.examole.jikmutest.global.dto.CommonResponse;
import com.examole.jikmutest.global.exception.CustomApiException;
import com.examole.jikmutest.user.jwt.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthenticationFilter extends OncePerRequestFilter {

	private final JwtUtil jwtUtil;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
		FilterChain filterChain) throws ServletException, IOException {

		try{
			if (request.getRequestURI().equals("/login") ||
				request.getRequestURI().equals("/signup")
			) {
				log.info("요청 제외 필터");
				filterChain.doFilter(request, response);
				return;
			}

			String authHeader = request.getHeader("Authorization");

			log.info("Authorization header: {}", authHeader);

			if (authHeader != null) {
				jwtUtil.validateToken(authHeader);
				Claims claims = jwtUtil.parseClaims(authHeader);
				UUID userId = UUID.fromString(claims.getSubject());
				String role = claims.get("role", String.class);

				UsernamePasswordAuthenticationToken authentication =
					new UsernamePasswordAuthenticationToken(userId, null, List.of(new SimpleGrantedAuthority("ROLE_" + role)));

				SecurityContextHolder.getContext().setAuthentication(authentication);
				log.info("인증 성공: userId={}, role={}", userId, role);
			} else {
				log.warn("유효하지 않은 토큰 또는 토큰 없음");
			}

			filterChain.doFilter(request, response);
		}catch (CustomApiException e) {

			response.setStatus(e.getStatus().value());
			response.setContentType("application/json;charset=UTF-8");

			CommonResponse<Object> errorResponse = new CommonResponse<>(e.getServiceCode());

			ObjectMapper objectMapper = new ObjectMapper();
			String json = objectMapper.writeValueAsString(errorResponse);
			response.getWriter().write(json);
		}

	}
}
