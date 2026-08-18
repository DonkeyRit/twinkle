package com.github.donkeyrit.twinkle.auth.services;

import com.github.donkeyrit.twinkle.dal.common.models.Page;
import com.github.donkeyrit.twinkle.dal.common.models.Paging;
import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Minimal in-memory stand-in for the real (Hibernate/jOOQ-backed) UserRepository,
 * so DefaultLoginService's business logic can be unit-tested without a database.
 */
class InMemoryUserRepository implements UserRepository {

	private final List<User> users = new ArrayList<>();
	private int nextId = 1;
	private boolean failNextSave = false;

	void failNextSave() {
		this.failNextSave = true;
	}

	@Override
	public Stream<User> getList(UserInfoSpecifciation querySpecification) {
		return users.stream().filter(user -> matches(user, querySpecification));
	}

	@Override
	public Optional<User> get(UserInfoSpecifciation querySpecification) {
		return getList(querySpecification).findFirst();
	}

	@Override
	public Page<User> getPagedResult(UserInfoSpecifciation filter, Optional<Paging> paging) {
		throw new UnsupportedOperationException("Not needed for these tests.");
	}

	@Override
	public User findById(int id) {
		return users.stream().filter(user -> user.getId() == id).findFirst().orElse(null);
	}

	@Override
	public Stream<User> findAll() {
		return users.stream();
	}

	@Override
	public boolean save(User user) {
		if (failNextSave) {
			failNextSave = false;
			return false;
		}
		user.setId(nextId++);
		users.add(user);
		return true;
	}

	@Override
	public boolean delete(User user) {
		return users.removeIf(existing -> existing.getId() == user.getId());
	}

	@Override
	public boolean update(User user) {
		for (int i = 0; i < users.size(); i++) {
			if (users.get(i).getId() == user.getId()) {
				users.set(i, user);
				return true;
			}
		}
		return false;
	}

	private boolean matches(User user, UserInfoSpecifciation specification) {
		if (specification.getLogin() != null && !specification.getLogin().equals(user.getLogin())) {
			return false;
		}
		if (specification.getPasswordHash() != null && !specification.getPasswordHash().equals(user.getPassword())) {
			return false;
		}
		return true;
	}
}
