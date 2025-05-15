package com.examole.jikmutest.user.repository;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.examole.jikmutest.user.entity.User;

@Repository
public class UserRepository {

	private final Map<UUID, User> memoryDb = new HashMap<>();

	public User save(User user) {
		memoryDb.put(user.getId(), user);
		return user;
	}

	public boolean existsByUsername(String username) {
		return memoryDb.values().stream()
			.anyMatch(user -> user.getUsername().equals(username));
	}
}
