package com.examole.jikmutest.user.domain.repository;

import com.examole.jikmutest.user.domain.model.User;

public interface UserRepository {
	boolean existsByUsername(String username);

	User save(User user);

	User findByUsername(String username);
}
