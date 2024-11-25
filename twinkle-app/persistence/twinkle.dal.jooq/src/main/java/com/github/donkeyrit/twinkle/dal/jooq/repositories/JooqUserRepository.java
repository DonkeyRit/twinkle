package com.github.donkeyrit.twinkle.dal.jooq.repositories;

import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.records.UsersRecord;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Users;

import com.github.donkeyrit.twinkle.dal.jooq.abstractions.JooqFilterableRepository;
import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;
import com.github.donkeyrit.twinkle.dal.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;

import com.google.inject.Inject;

import static org.jooq.impl.DSL.field;
import org.jooq.Condition;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

public class JooqUserRepository 
	extends JooqFilterableRepository<User, UserInfoSpecifciation, UsersRecord>
	implements UserRepository {

	@Inject
	public JooqUserRepository(DSLContext dslContext) {
		super(dslContext, Users.USERS);
	}

	@Override
	public Condition toCondition(UserInfoSpecifciation querySpecification) {
		Condition loginCondition = (querySpecification.getLogin() != null) ? 
			field(Users.USERS.LOGIN).eq(querySpecification.getLogin()) : 
			DSL.noCondition();

		Condition passwordHashCondition = (querySpecification.getPasswordHash() != null) ? 
			field(Users.USERS.PASSWORD).eq(querySpecification.getPasswordHash()) : 
			DSL.noCondition();

		return loginCondition.and(passwordHashCondition);
	}

	@Override
	protected User mapRecordToEntity(UsersRecord record) {
		User user = new User(record.getLogin(), record.getPassword(), record.getRole());
		user.setId(record.getId());
		return user;
	}

	@Override
	protected UsersRecord entityToRecord(User entity) {
		return new UsersRecord(entity.getId(), entity.getLogin(), entity.getPassword(), entity.isRole());
	}
}
