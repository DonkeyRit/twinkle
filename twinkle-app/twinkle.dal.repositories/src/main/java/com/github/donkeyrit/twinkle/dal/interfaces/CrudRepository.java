package com.github.donkeyrit.twinkle.dal.interfaces;

import java.util.stream.Stream;

public interface CrudRepository<T extends BaseDbModel, T1 extends QueryFilter> 
{
	T getById(int id);
	Stream<T> getList(T1 queryFilter);
	boolean insert(T o);
	boolean update(T o);
	boolean delete(T o);
}
