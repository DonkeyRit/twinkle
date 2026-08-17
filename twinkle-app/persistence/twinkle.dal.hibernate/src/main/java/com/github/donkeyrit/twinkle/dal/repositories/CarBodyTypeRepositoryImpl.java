package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.CarBodyTypeRepository;
import com.github.donkeyrit.twinkle.dal.models.CarBodyType;
import com.github.donkeyrit.twinkle.dal.repositories.abstractions.HibernateGenericRepository;

import com.google.inject.Inject;

import jakarta.persistence.EntityManager;

public class CarBodyTypeRepositoryImpl extends HibernateGenericRepository<CarBodyType> implements CarBodyTypeRepository {

	@Inject
	public CarBodyTypeRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}
}
