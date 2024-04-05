package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;
import com.github.donkeyrit.twinkle.dal.models.User;

public interface UserRepository<TPredicate> extends FilterableRepository<User, TPredicate, UserInfoSpecifciation<TPredicate>>
{
	void updatePassword(int userId, String passwordHash);
}
