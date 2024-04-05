package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqFilterableRepository;
import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;
import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import com.google.inject.Inject;

import javax.sql.DataSource;
import org.jooq.Condition;

public class JooqUserRepository 
	extends JooqFilterableRepository<User, UserInfoSpecifciation<Condition>>
	implements UserRepository<Condition> {

	@Inject
	public JooqUserRepository(DataSource dataSource) {
		super(dataSource);
	}

	@Override
	public void updatePassword(int userId, String passwordHash) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'updatePassword'");
	}
}
