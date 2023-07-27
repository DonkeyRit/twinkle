package com.github.donkeyrit.twinkle.dal.contracts;

import jakarta.persistence.EntityManager;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.stream.Stream;

public abstract class BaseCrudRepository<T extends BaseDbModel> implements CrudRepository<T>
{
	protected EntityManager session;
	private final Class<T> entityClass;

	@SuppressWarnings("unchecked")
	public BaseCrudRepository(EntityManager session) 
	{
		this.session = session;
		Type genericSuperclass = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType = (ParameterizedType) genericSuperclass;
        Type[] typeArguments = parameterizedType.getActualTypeArguments();
        this.entityClass = (Class<T>) typeArguments[0];
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
		try
		{
			session.getTransaction().begin();
			session.merge(o);
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
	public T getById(int id) 
	{
		return session.find(entityClass, id);
	}

	@Override
	public Stream<T> getList(QueryFilter queryFilter) 
	{
		return session
			.createQuery("SELECT e FROM " + entityClass.getSimpleName() + " e", entityClass)
            .getResultStream();
	}
}
