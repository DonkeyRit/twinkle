package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.ResultingInjuryRepository;
import com.github.donkeyrit.twinkle.dal.models.ResultingInjury;
import com.github.donkeyrit.twinkle.dal.repositories.abstractions.HibernateGenericRepository;

import com.google.inject.Inject;

import jakarta.persistence.EntityManager;

public class ResultingInjuryRepositoryImpl extends HibernateGenericRepository<ResultingInjury> implements ResultingInjuryRepository {

	@Inject
	public ResultingInjuryRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}
}
