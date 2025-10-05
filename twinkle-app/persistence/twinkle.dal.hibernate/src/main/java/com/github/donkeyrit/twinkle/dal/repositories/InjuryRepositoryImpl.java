package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.InjuryRepository;
import com.github.donkeyrit.twinkle.dal.models.Injury;
import com.github.donkeyrit.twinkle.dal.repositories.abstractions.HibernateGenericRepository;

import com.google.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class InjuryRepositoryImpl extends HibernateGenericRepository<Injury> implements InjuryRepository {

	@Inject
	public InjuryRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public Optional<Injury> getByName(String injuryName) {
		TypedQuery<Injury> query = entityManager.createQuery(
			"SELECT i FROM Injury i WHERE i.injuryName = :injuryName", Injury.class);
		query.setParameter("injuryName", injuryName);
		try {
			return Optional.of(query.getSingleResult());
		} catch (NoResultException e) {
			return Optional.empty();
		}
	}
}
