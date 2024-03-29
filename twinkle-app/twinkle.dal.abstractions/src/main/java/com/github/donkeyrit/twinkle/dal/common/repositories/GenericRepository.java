package com.github.donkeyrit.twinkle.dal.common.repositories;

import com.github.donkeyrit.twinkle.dal.common.models.Identifiable;
import java.util.stream.Stream;

public interface GenericRepository<T extends Identifiable> 
{
	T findById(Long id);
	Stream<T> findAll();
	boolean save(T o);
	boolean delete(T o);
	boolean update(T o);
}
