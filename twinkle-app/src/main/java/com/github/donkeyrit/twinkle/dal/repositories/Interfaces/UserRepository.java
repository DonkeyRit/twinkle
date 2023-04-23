package com.github.donkeyrit.twinkle.dal.repositories.interfaces;

import com.github.donkeyrit.twinkle.dal.models.User;
import java.util.Optional;

public interface UserRepository 
{
    Optional<User> getByLoginAndPassword(String login, String password);
    boolean isUserExist(String login);
    void insert(User user);
}
