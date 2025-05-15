package com.examole.jikmutest.user.infrastructure.jwt;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.examole.jikmutest.global.exception.CustomApiException;
import com.examole.jikmutest.user.domain.model.UserRole;
import com.examole.jikmutest.user.application.exception.UserException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;

@Component
public class JwtUtil {

	private static final String BEARER_PREFIX = "Bearer ";

	private final Key key;
	private final long accessExpiration;

	public JwtUtil(
		@Value("${service.jwt.secret-key}") String secretKey,
		@Value("${service.jwt.access-expiration}") long accessExpiration
	) {
		byte[] decodedKey = Base64.getDecoder().decode(secretKey);
		this.key = Keys.hmacShaKeyFor(decodedKey);
		this.accessExpiration = accessExpiration;
	}

	public String createAccessToken(UUID userId, String userName, UserRole userRole) {

		return Jwts.builder()
			.setSubject(String.valueOf(userId))
			.claim("userId", userId)
			.claim("userName", userName)
			.claim("role", userRole)
			.setIssuedAt(new Date())
			.setExpiration(new Date(System.currentTimeMillis() + accessExpiration))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public void validateToken(String token) {
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(removePrefix(token));
		} catch (SecurityException | MalformedJwtException | SignatureException e) {
			throw new CustomApiException(UserException.INVALID_JWT_SIGNATURE);
		} catch (ExpiredJwtException e) {
			throw new CustomApiException(UserException.EXPIRED_JWT_TOKEN);
		} catch (UnsupportedJwtException e) {
			throw new CustomApiException(UserException.UNSUPPORTED_JWT_TOKEN);
		} catch (IllegalArgumentException e) {
			throw new CustomApiException(UserException.JWT_CLAIM_IS_EMPTY);
		}
	}

	public Claims parseClaims(String token) {
		return Jwts.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(removePrefix(token))
			.getBody();
	}

	public String removePrefix(String token) {
		if (token != null && token.startsWith(BEARER_PREFIX)) {
			return token.substring(BEARER_PREFIX.length());
		}

		return token;
	}

}