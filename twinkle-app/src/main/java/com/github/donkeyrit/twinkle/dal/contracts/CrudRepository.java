package com.github.donkeyrit.twinkle.dal.contracts;

import java.util.stream.Stream;

public interface CrudRepository<T extends BaseDbModel> 
{
	T getById(int id);
	Stream<T> getList(QueryFilter queryFilter);
	boolean insert(T o);
	boolean update(T o);
	boolean delete(T o);
}
