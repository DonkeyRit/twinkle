package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.InjuryRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Injury1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import com.google.inject.Inject;
import java.util.Optional;

public class InjuryRepositoryImpl extends BaseCrudRepository<Injury1, QueryFilter> implements InjuryRepository {

	@Inject
	public InjuryRepositoryImpl(EntityManager session) {
		super(session);
	}

	@Override
	public Optional<Injury1> getByName(String injuryName) {
		TypedQuery<Injury1> query = session.createQuery(
			"SELECT i FROM Injury1 i WHERE i.injuryName = :injuryName", Injury1.class);
		query.setParameter("injuryName", injuryName);
		try {
			return Optional.of(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
}
