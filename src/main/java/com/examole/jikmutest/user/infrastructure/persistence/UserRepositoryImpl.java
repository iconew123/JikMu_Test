package com.examole.jikmutest.user.infrastructure.persistence;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.examole.jikmutest.user.domain.model.User;
import com.examole.jikmutest.user.domain.repository.UserRepository;

@Repository
public class UserRepositoryImpl implements UserRepository {

	private final Map<UUID, User> memoryDb = new HashMap<>();

	public User save(User user) {
		memoryDb.put(user.getId(), user);
		return user;
	}

	public boolean existsByUsername(String username) {
		return memoryDb.values().stream()
			.anyMatch(user -> user.getUsername().equals(username));
	}

	public User findByUsername(String username) {
		return memoryDb.values().stream()
			.filter(user -> user.getUsername().equals(username))
			.findFirst()
			.orElse(null);
	}
}
