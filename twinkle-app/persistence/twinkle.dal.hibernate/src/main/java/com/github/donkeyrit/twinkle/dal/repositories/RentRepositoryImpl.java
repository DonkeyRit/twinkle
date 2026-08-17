package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.interfaces.RentRepository;
import com.github.donkeyrit.twinkle.dal.models.Rent;
import com.github.donkeyrit.twinkle.dal.repositories.abstractions.HibernateGenericRepository;

import com.google.inject.Inject;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class RentRepositoryImpl extends HibernateGenericRepository<Rent> implements RentRepository {

	@Inject
	public RentRepositoryImpl(EntityManager entityManager) {
		super(entityManager);
	}

	@Override
	public boolean isTaken(long carId) {
		return getLastByCarId((int) carId)
			.map(rent -> rent.getEndDate() == null)
			.orElse(false);
	}

	@Override
	public Optional<Rent> getLastByCarId(int carId) {
		TypedQuery<Rent> query = entityManager.createQuery(
			"SELECT r FROM Rent r WHERE r.idCar = :carId ORDER BY r.endDate DESC NULLS FIRST, r.planDate DESC", Rent.class);
		query.setParameter("carId", carId);
		query.setMaxResults(1);
		return query.getResultStream().findFirst();
	}

	@Override
	public Optional<Rent> getLastByClientId(int clientId) {
		TypedQuery<Rent> query = entityManager.createQuery(
			"SELECT r FROM Rent r WHERE r.idClient = :clientId ORDER BY r.endDate DESC NULLS FIRST, r.planDate DESC", Rent.class);
		query.setParameter("clientId", clientId);
		query.setMaxResults(1);
		return query.getResultStream().findFirst();
	}
}
