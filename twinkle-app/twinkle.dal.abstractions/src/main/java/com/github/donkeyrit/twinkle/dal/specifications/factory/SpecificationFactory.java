package com.github.donkeyrit.twinkle.dal.specifications.factory;

import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;

public interface SpecificationFactory<TPredicate> {

	UserInfoSpecifciation<TPredicate> createUserInfoSpecification(String login);
	UserInfoSpecifciation<TPredicate> createUserInfoSpecification(String login, String passwordHash);
}
