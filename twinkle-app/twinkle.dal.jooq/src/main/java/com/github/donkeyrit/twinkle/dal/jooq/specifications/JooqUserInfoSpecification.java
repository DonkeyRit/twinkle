package com.github.donkeyrit.twinkle.dal.jooq.specifications;

import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;
import com.github.donkeyrit.twinkle.dal.jooq.generated.tables.Users;

import static org.jooq.impl.DSL.field;

import org.jooq.Condition;
import org.jooq.impl.DSL;

public class JooqUserInfoSpecification extends UserInfoSpecifciation<Condition> {

	public JooqUserInfoSpecification(String login) {
		super(login);
	}

	public JooqUserInfoSpecification(String login, String passwordHash) {
		super(login, passwordHash);
	}


	@Override
	public Condition toCondition() {
		
		Condition loginCondition = (login != null) ? 
			field(Users.USERS.LOGIN).eq(login) : 
			DSL.noCondition();

		Condition passwordHashCondition = (passwordHash != null) ? 
			field(Users.USERS.PASSWORD).eq(passwordHash) : 
			DSL.noCondition();

		return loginCondition.and(passwordHashCondition);
	}
}
