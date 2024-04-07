package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.FilterableRepository;
import com.github.donkeyrit.twinkle.dal.specifications.UserInfoSpecifciation;
import com.github.donkeyrit.twinkle.dal.models.User;

public interface UserRepository extends FilterableRepository<User, UserInfoSpecifciation>
{
	void updatePassword(int userId, String passwordHash);
}
