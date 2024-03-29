package com.github.donkeyrit.twinkle.dal.common.repositories;

import java.util.List;

import com.github.donkeyrit.twinkle.dal.common.Identifiable;

public interface GenericRepository<T extends Identifiable> 
{
	T findById(Long id);
	List<T> findAll();
	boolean save(T o);
	boolean delete(T o);
	boolean update(T o);
}
