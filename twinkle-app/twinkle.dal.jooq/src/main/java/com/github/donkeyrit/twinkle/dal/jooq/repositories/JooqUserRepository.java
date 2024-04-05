package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.common.specifications.QuerySpecification;
import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;
import com.google.inject.Inject;

import java.util.Optional;
import java.util.stream.Stream;
import javax.sql.DataSource;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class JooqUserRepository implements UserRepository {

	private final DSLContext dsl;

	@Inject
	public JooqUserRepository(DataSource dataSource) {
		this.dsl = DSL.using(dataSource, SQLDialect.POSTGRES);
	}

	@Override
	public User findById(int id) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findById'");
	}

	@Override
	public Stream<User> findAll() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'findAll'");
	}

	@Override
	public boolean save(User o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'save'");
	}

	@Override
	public boolean delete(User o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

	@Override
	public boolean update(User o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public void updatePassword(int userId, String passwordHash) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updatePassword'");
	}

	@Override
	public Stream<User> getList(UserInfoSpecifciation querySpecification) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getList'");
	}

	@Override
	public Optional<User> get(UserInfoSpecifciation quSpecification) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'get'");
	}
	
}
