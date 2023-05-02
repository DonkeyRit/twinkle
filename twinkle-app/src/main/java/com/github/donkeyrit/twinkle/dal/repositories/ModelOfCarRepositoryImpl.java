package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ModelOfCarRepository;
import com.github.donkeyrit.twinkle.dal.models.ModelOfCar;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.stream.Stream;

public class ModelOfCarRepositoryImpl implements ModelOfCarRepository
{
	private EntityManager session;
    
    public ModelOfCarRepositoryImpl(EntityManager session) 
	{
        this.session = session;
    }

	@Override
	public Stream<ModelOfCar> getListByMark(int markId) 
	{
		TypedQuery<ModelOfCar> query = session.createQuery("SELECT u FROM ModelOfCar u WHERE u.markId = :markId", ModelOfCar.class);
        query.setParameter("markId", markId);
		return query.getResultStream();
	}
}
