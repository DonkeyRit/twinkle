package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.interfaces.CrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.User;
import java.util.Optional;

public interface UserRepository extends CrudRepository<User, QueryFilter>
{
    Optional<User> getByLoginAndPassword(String login, String password);
    boolean isUserExist(String login);
	void updatePassword(int userId, String passwordHash);
}
