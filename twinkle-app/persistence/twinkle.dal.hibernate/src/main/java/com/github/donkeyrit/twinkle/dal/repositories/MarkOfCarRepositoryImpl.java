package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.MarkOfCarRepository;
import com.github.donkeyrit.twinkle.dal.models.MarkOfCar;
import com.github.donkeyrit.twinkle.dal.repositories.abstractions.HibernateGenericRepository;

import com.google.inject.Inject;

import jakarta.persistence.EntityManager;

public class MarkOfCarRepositoryImpl extends HibernateGenericRepository<MarkOfCar> implements MarkOfCarRepository {

	@Inject
	public MarkOfCarRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}
}
