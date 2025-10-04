package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.ResultingInjuryRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.ResultingInjury1;

import jakarta.persistence.EntityManager;

import com.google.inject.Inject;

public class ResultingInjuryRepositoryImpl extends BaseCrudRepository<ResultingInjury1, QueryFilter> implements ResultingInjuryRepository {

	@Inject
	public ResultingInjuryRepositoryImpl(EntityManager session) {
		super(session);
	}
}
