package com.github.donkeyrit.twinkle.dal.repositories;

import com.github.donkeyrit.twinkle.dal.repositories.interfaces.RentRepository;
import com.google.inject.Inject;
import com.github.donkeyrit.twinkle.dal.interfaces.BaseCrudRepository;
import com.github.donkeyrit.twinkle.dal.interfaces.QueryFilter;
import com.github.donkeyrit.twinkle.dal.models.Rent1;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.Optional;

public class RentRepositoryImpl extends BaseCrudRepository<Rent1, QueryFilter> implements RentRepository {

	@Inject
	public RentRepositoryImpl(EntityManager session) {
		super(session);
	}

	@Override
	public boolean isTaken(int carId) {

		TypedQuery<Rent1> query = session.createQuery(
			"SELECT u FROM Rent u WHERE u.idCar = :carId ORDER BY u.endDate", Rent1.class);
        query.setParameter("carId", carId);
		query.setMaxResults(1);

		if(query.getFirstResult() == 0){
			return false;
		}

		Rent1 lastRent = query.getSingleResult();
		return lastRent.getEndDate() == null;
	}

	@Override
	public Optional<Rent1> getLastByCarId(int carId) {
		TypedQuery<Rent1> query = session.createQuery(
			"SELECT r FROM Rent1 r WHERE r.idCar = :carId ORDER BY r.endDate, r.planDate DESC", Rent1.class);
		query.setParameter("carId", carId);
		query.setMaxResults(1);
		return query.getResultStream().findFirst();
	}

	@Override
	public Optional<Rent1> getLastByClientId(int clientId) {
		TypedQuery<Rent1> query = session.createQuery(
			"SELECT r FROM Rent1 r WHERE r.idClient = :clientId ORDER BY r.endDate, r.planDate DESC", Rent1.class);
		query.setParameter("clientId", clientId);
		query.setMaxResults(1);
		return query.getResultStream().findFirst();
	}
}
