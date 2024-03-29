package com.github.donkeyrit.twinkle.dal.interfaces;

import com.github.donkeyrit.twinkle.dal.common.repositories.GenericRepository;
import com.github.donkeyrit.twinkle.dal.models.User;
import java.util.Optional;

public interface UserRepository extends GenericRepository<User>
{
    Optional<User> getByLoginAndPassword(String login, String password);
    boolean isUserExist(String login);
	void updatePassword(int userId, String passwordHash);
}
