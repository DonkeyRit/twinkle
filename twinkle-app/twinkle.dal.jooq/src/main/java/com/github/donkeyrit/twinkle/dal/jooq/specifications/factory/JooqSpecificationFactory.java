package com.github.donkeyrit.twinkle.dal.jooq.specifications.factory;

import com.github.donkeyrit.twinkle.dal.jooq.specifications.JooqUserInfoSpecification;
import com.github.donkeyrit.twinkle.dal.specifications.factory.SpecificationFactory;
import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;

import org.jooq.Condition;

public class JooqSpecificationFactory implements SpecificationFactory<Condition> {

	@Override
	public UserInfoSpecifciation<Condition> createUserInfoSpecification(String login) {
		return new JooqUserInfoSpecification(login);
	}

	@Override
	public UserInfoSpecifciation<Condition> createUserInfoSpecification(String login, String passwordHash) {
		return new JooqUserInfoSpecification(login, passwordHash);
	}
	
}
