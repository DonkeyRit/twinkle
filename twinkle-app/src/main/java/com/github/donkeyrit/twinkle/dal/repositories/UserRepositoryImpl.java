package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.Interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class UserRepositoryImpl implements UserRepository
{
    private EntityManager session;
    
    public UserRepositoryImpl(EntityManager session) {
        this.session = session;
    }

    @Override
    public Optional<User> getByLoginAndPassword(String login, String password) 
    {
        session.getTransaction().begin();
        Optional<User> result = Optional.empty();
        TypedQuery<User> query = session.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password = :password", User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        try 
        {
            User user = query.getSingleResult();
            result = Optional.of(user);
        } 
        catch (NoResultException e) 
        {

        }
        
        session.getTransaction().commit();
        return result;
    }
    
}
