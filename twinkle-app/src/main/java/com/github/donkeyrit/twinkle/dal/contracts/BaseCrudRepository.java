package com.github.donkeyrit.twinkle.dal.contracts;

import jakarta.persistence.EntityManager;

public abstract class BaseCrudRepository<T extends BaseDbModel> implements CrudRepository<T>
{
	protected EntityManager session;

	public BaseCrudRepository(EntityManager session) 
	{
		this.session = session;
	}

	@Override
	public boolean insert(T o) 
	{
		try
		{
			session.getTransaction().begin();
			session.persist(o);
			session.getTransaction().commit();
			return true;
		}
		catch (Exception ex)
		{
			//TODO: Implement logger
			System.out.printf("Exception occured in %s during inserting. Exception - %s", this,getClass().getName(), ex);
		}

		return false;
	}

	@Override
	public boolean update(T o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public boolean delete(T o) 
	{
		try
		{
			session.getTransaction().begin();
			session.remove(o);
			session.getTransaction().commit();
			return true;
		}
		catch (Exception ex)
		{
			//TODO: Implement logger
			System.out.printf("Exception occured in %s during removing. Exception - %s", this,getClass().getName(), ex);
		}

		return false;
	}

	@Override
	public T getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
