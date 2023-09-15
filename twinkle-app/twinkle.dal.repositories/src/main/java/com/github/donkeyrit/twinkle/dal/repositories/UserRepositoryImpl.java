package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.UserRepository;
import com.github.donkeyrit.twinkle.dal.models.User;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import com.google.inject.Inject;
import java.util.Optional;

public class UserRepositoryImpl implements UserRepository
{
    private EntityManager session;
    
	@Inject
    public UserRepositoryImpl(EntityManager session) 
	{
        this.session = session;
    }

    @Override
    public Optional<User> getByLoginAndPassword(String login, String password) 
    {
        TypedQuery<User> query = session.createQuery("SELECT u FROM User u WHERE u.login = :login AND u.password = :password", User.class);
        query.setParameter("login", login);
        query.setParameter("password", password);
        try 
        {
            User user = query.getSingleResult();
            return Optional.of(user);
        } 
        catch (NoResultException e) 
        {
            return Optional.empty();
        }
    }

    @Override
    public boolean isUserExist(String login) 
    {
        TypedQuery<User> query = session.createQuery("SELECT u FROM User u WHERE u.login = :login", User.class);
        query.setParameter("login", login);
        try 
        {
            query.getSingleResult();
            return true;
        } 
        catch (NoResultException e) 
        {
            return false;
        }
    }

    @Override
    public void insert(User user) 
    {
        session.getTransaction().begin();
        session.persist(user);
        session.getTransaction().commit();
    }

	@Override
	public void updatePassword(int userId, String passwordHash) {
		session.getTransaction().begin();
        User user = session.find(User.class, userId);
		user.setPassword(passwordHash);
        session.getTransaction().commit();
	}
}
