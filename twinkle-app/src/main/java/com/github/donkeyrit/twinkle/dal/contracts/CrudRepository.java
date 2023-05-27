package com.github.donkeyrit.twinkle.dal.contracts;

import java.util.List;

public interface CrudRepository<T extends BaseDbModel> 
{
	T getById(int id);
	List<T> getList(QueryFilter queryFilter);
	boolean insert(T o);
	boolean update(T o);
	boolean delete(T o);
}
