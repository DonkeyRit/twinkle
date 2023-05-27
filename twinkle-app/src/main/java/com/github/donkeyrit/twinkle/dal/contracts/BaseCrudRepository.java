package com.github.donkeyrit.twinkle.dal.contracts;

import jakarta.persistence.EntityManager;
import java.util.List;

public abstract class BaseCrudRepository<T extends BaseDbModel> implements CrudRepository<T>
{
	protected EntityManager session;

	public BaseCrudRepository(EntityManager session) 
	{
		this.session = session;
	}

	@Override
	public boolean insert(T o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'insert'");
	}

	@Override
	public boolean update(T o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'update'");
	}

	@Override
	public boolean delete(T o) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'delete'");
	}

	@Override
	public T getById(int id) {
		// TODO Auto-generated method stub
		return null;
	}
}
